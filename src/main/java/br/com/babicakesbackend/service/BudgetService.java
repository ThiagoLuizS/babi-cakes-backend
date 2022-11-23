package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.BudgetForm;
import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.BudgetProductReserved;
import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.entity.Inventory;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import br.com.babicakesbackend.models.mapper.BudgetMapperImpl;
import br.com.babicakesbackend.models.mapper.BudgetProductReservedMapperImpl;
import br.com.babicakesbackend.models.mapper.InventoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class BudgetService extends AbstractService<Budget, BudgetView, BudgetForm> {

    private final BudgetRepository repository;
    private final BudgetMapperImpl budgetMapper;
    private final AuthenticationService authenticationService;
    private final ProductService productService;
    private final InventoryService inventoryService;
    private final InventoryMapperImpl inventoryMapper;
    private final BudgetProductReservedService budgetProductReservedService;
    private final CupomService cupomService;
    private final BudgetProductReservedMapperImpl budgetProductReservedMapper;

    public void createNewBudget(String authorization, String cupomCode, List<BudgetProductReservedForm> reservedForms) {
        try {
            Optional<Cupom> cupom = Optional.empty();

            User user = authenticationService.getUser(authorization);

            Optional<Budget> budgetSave = repository.findTop1ByOrderByCodeDesc();

            Budget budget = Budget.builder()
                    .code(budgetSave.isPresent() ? budgetSave.get().getCode() + 1 : 1)
                    .user(user)
                    .budgetStatusEnum(BudgetStatusEnum.AWAITING_PAYMENT)
                    .dateCreateBudget(new Date())
                    .build();

            if(StringUtils.isNotBlank(cupomCode)) {
               cupom = cupomService.isCupomValid(cupomCode);
            }

            if(cupom.isPresent()) {
                budget.setCupom(cupom.get());
            }

            List<BudgetProductReserved> budgetProductReserveds = reservedForms.stream()
                    .map(form -> createBudgetProductReserved(form))
                    .collect(Collectors.toList());

            BigDecimal amount = budgetProductReserveds.stream()
                    .map(this::calculatedProduct).reduce(BigDecimal.ZERO, BigDecimal::add);

            budget.setAmount(amount);

            repository.save(budget);

            budgetProductReserveds.stream().forEach(item -> item.setBudget(budget));

            budgetProductReservedService.saveAll(budgetProductReserveds);

        }catch (Exception e) {
            log.error(">> createNewBudget [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public void canceledBudget(Long budgetCode) {
        try {
            Optional<Budget> budget = repository.findByCode(budgetCode);

            if(!budget.isPresent()) {
                throw new GlobalException("Orçamento/Pedido não encontrado");
            }

            List<BudgetProductReserved> productReserveds = budgetProductReservedService.findByBudget(budget.get());

            productReserveds.stream().forEach(reserved -> {
                Optional<Inventory> inventory = inventoryService.findByProductId(reserved.getProduct().getId());
                inventory.get().setQuantity(inventory.get().getQuantity() + reserved.getQuantity());
                inventoryService.saveByEntityCustom(inventory.get());
            });

            budget.get().setBudgetStatusEnum(BudgetStatusEnum.CANCELED_ORDER);
            budget.get().setDateFinalizedBudget(new Date());

            repository.save(budget.get());

        } catch (Exception e) {
            log.error("<< canceledBudget [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public Page<BudgetView> findBudgetPageByUser(String authorization, Pageable pageable) {
        User user = authenticationService.getUser(authorization);
        log.info(">> findBudgetUser [user={}, pageable={}]", user.getEmail(), pageable);

        Page<Budget> page = repository.findByUser(user, pageable);
        log.info("<< findBudgetUser [page size={}]", page.getSize());

        List<BudgetView> views = page.getContent().stream().map(budgetMapper::entityToView)
                .collect(Collectors.toList());

        views.stream().forEach(budget -> {
            List<BudgetProductReserved> list = budgetProductReservedService.findByBudgetCode(budget.getCode());
            budget.setProductReservedViewList(list.stream()
                    .map(budgetProductReservedMapper::entityToView).collect(Collectors.toList()));
        });

        return new PageImpl<>(new ArrayList<>(views));
    }

    public BigDecimal calculatedProduct(BudgetProductReserved budgetProductReserved) {
        Integer quantity = budgetProductReserved.getQuantity();
        if(budgetProductReserved.getProduct().isExistPercentage()) {
            return budgetProductReserved.getProduct().getDiscountValue().multiply(new BigDecimal(quantity));
        }
        return budgetProductReserved.getProduct().getValue().multiply(new BigDecimal(quantity));
    }

    public BudgetProductReserved createBudgetProductReserved(BudgetProductReservedForm form) {
        Optional<Product> product = productService.findByCode(form.getProductCode());

        if(!product.isPresent()) {
            throw new GlobalException("Produto com o código " + form.getProductCode() + " não existe");
        }

        if(product.get().isWithStock()) {
            Optional<Inventory> inventory = inventoryService.findByProductId(product.get().getId());
            if(!inventory.isPresent()) {
                throw new GlobalException("Produto " + product.get().getName() + " está em falta");
            }

            boolean isQuantityValid = inventory.get().getQuantity() >= form.getQuantity();

            if(!isQuantityValid) {
                throw new GlobalException("Produto " + product.get().getName() + " tem apenas " + inventory.get().getQuantity() + " em estoque.");
            }

            inventory.get().setQuantity(inventory.get().getQuantity() -  form.getQuantity());

            inventoryService.update(inventoryMapper.entityToForm(inventory.get()));
        }

        return BudgetProductReserved.builder()
                .product(product.get())
                .quantity(form.getQuantity())
                .build();
    }

    @Override
    protected JpaRepository<Budget, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Budget, BudgetView, BudgetForm> getConverter() {
        return budgetMapper;
    }
}
