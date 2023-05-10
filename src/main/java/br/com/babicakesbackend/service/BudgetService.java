package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.BudgetForm;
import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.NotificationForm;
import br.com.babicakesbackend.models.entity.Address;
import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.BudgetProductReserved;
import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.entity.Inventory;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import br.com.babicakesbackend.models.enumerators.PixStatusEnum;
import br.com.babicakesbackend.models.mapper.BudgetMapperImpl;
import br.com.babicakesbackend.models.mapper.BudgetProductReservedMapperImpl;
import br.com.babicakesbackend.models.mapper.InventoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.BudgetRepository;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
    private final AddressService addressService;
    private final FirebaseService firebaseService;

    private final ParameterizationService parameterizationService;

    public BudgetView createNewBudget(String authorization, String cupomCode, List<BudgetProductReservedForm> reservedForms) {
        try {
            Optional<Cupom> cupom = Optional.empty();

            User user = authenticationService.getUser(authorization);

            Optional<Budget> budgetSave = repository.findTop1ByOrderByCodeDesc();

            Optional<Address> address = addressService.findByUserAndAddressMainIsTrue(user);

            if(!address.isPresent()) {
                throw new GlobalException("Informe um endereço principal ou cadastre um novo");
            }

            Budget budget = Budget.builder()
                    .code(budgetSave.isPresent() ? budgetSave.get().getCode() + 1 : 1)
                    .user(user)
                    .budgetStatusEnum(BudgetStatusEnum.AWAITING_PAYMENT)
                    .dateCreateBudget(new Date())
                    .build();

            if(StringUtils.isNotBlank(cupomCode)) {
               cupom = cupomService.isCupomValid(cupomCode);
            }

            List<BudgetProductReserved> budgetProductReserveds = reservedForms.stream()
                    .map(form -> createBudgetProductReserved(form))
                    .collect(Collectors.toList());

            Optional<Cupom> finalCupom = cupom;
            BigDecimal subTotal = budgetProductReserveds.stream()
                    .map(item -> calculatedProduct(item, finalCupom.isPresent())).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal freightCost = parameterizationService.findFreightCost();

            budget.setSubTotal(subTotal);
            budget.setFreightCost(freightCost);
            budget.setAddress(address.get());

            BigDecimal amount = BigDecimal.ZERO;
            amount = amount.add(subTotal);
            amount = amount.add(freightCost);

            if(cupom.isPresent()) {
                if(cupom.get().isCupomIsValueMin() && cupom.get().getCupomValueMin().compareTo(subTotal) > 0) {
                    throw new GlobalException("O cupom não é aplicável, pois o valor do pedido está abaixo do valor minimo.");
                }
                amount = amount.subtract(cupom.get().getCupomValue());
                budget.setCupom(cupom.get());
                cupomService.applyCoupon(cupom.get());
            }

            budget.setAmount(amount);
            budget.setUser(user);

            Budget budgetSaveNew = repository.saveAndFlush(budget);

            budgetProductReserveds.stream().forEach(item -> item.setBudget(budget));

            budgetProductReservedService.saveAll(budgetProductReserveds);

            repository.flush();

            Optional<Budget> budgetOpt = repository.findByCode(budgetSaveNew.getCode());

            return getConverter().entityToView(budgetOpt.get());

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

            if(Objects.nonNull(budget.get().getCupom())) {
                cupomService.activateCoupon(budget.get().getCupom());
            }

            repository.save(budget.get());

        } catch (Exception e) {
            log.error("<< canceledBudget [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public void paidBudget(Long budgetCode) {
        Optional<Budget> budget = repository.findByCode(budgetCode);

        if(!budget.isPresent()) {
            throw new GlobalException("Orçamento/Pedido não encontrado");
        }

        budget.get().setBudgetStatusEnum(BudgetStatusEnum.PAID_OUT);
        budget.get().setDateFinalizedBudget(new Date());
        repository.saveAndFlush(budget.get());
    }

    public Budget saveCustom(Budget budget) {
        return repository.save(budget);
    }

    public void preparingBudget(Long budgetCode) {
        Optional<Budget> budget = repository.findByCode(budgetCode);

        if(!budget.isPresent()) {
            throw new GlobalException("Orçamento/Pedido não encontrado");
        }

        budget.get().setBudgetStatusEnum(BudgetStatusEnum.PREPARING_ORDER);
        repository.saveAndFlush(budget.get());

        String title = "Prontinho, " + ConstantUtils.getFirstName(budget.get().getUser().getName()) + " !";
        String description = "Seu pedido está sendo preparado!";

        firebaseService.sendNewEventByUser(EventForm.builder()
                .title(title)
                .message(description)
                .image("")
                .build(), budget.get().getUser());
        firebaseService.sendNotificationByUser(NotificationForm.builder()
                .title(title)
                .message(description)
                .build(), budget.get().getUser().getId());
    }

    public void waitingForDelivery(Long budgetCode) {
        Optional<Budget> budget = repository.findByCode(budgetCode);

        if(!budget.isPresent()) {
            throw new GlobalException("Orçamento/Pedido não encontrado");
        }

        budget.get().setBudgetStatusEnum(BudgetStatusEnum.WAITING_FOR_DELIVERY);

        repository.saveAndFlush(budget.get());

        String title = "Seu pedido está pronto!";
        String description = "O seu pedido está pronto e estamos aguardando o entregador.";

        firebaseService.sendNewEventByUser(EventForm.builder()
                .title(title)
                .message(description)
                .image("")
                .build(), budget.get().getUser());
        firebaseService.sendNotificationByUser(NotificationForm.builder()
                .title(title)
                .message(description)
                .build(), budget.get().getUser().getId());
    }

    public void budgetIsOutForDelivery(Long budgetCode) {
        Optional<Budget> budget = repository.findByCode(budgetCode);

        if(!budget.isPresent()) {
            throw new GlobalException("Orçamento/Pedido não encontrado");
        }

        budget.get().setBudgetStatusEnum(BudgetStatusEnum.ORDER_IS_OUT_FOR_DELIVERY);

        repository.saveAndFlush(budget.get());

        String title = "Prontinho!";
        String description = "O seu pedido saiu para entrega";

        firebaseService.sendNewEventByUser(EventForm.builder()
                .title(title)
                .message(description)
                .image("")
                .build(), budget.get().getUser());
        firebaseService.sendNotificationByUser(NotificationForm.builder()
                .title(title)
                .message(description)
                .build(), budget.get().getUser().getId());
    }

    public void budgetDelivery(Long budgetCode) {
        Optional<Budget> budget = repository.findByCode(budgetCode);

        if(!budget.isPresent()) {
            throw new GlobalException("Orçamento/Pedido não encontrado");
        }

        budget.get().setBudgetStatusEnum(BudgetStatusEnum.ORDER_DELIVERED);

        repository.saveAndFlush(budget.get());

        String title = "Olá, " + ConstantUtils.getFirstName(budget.get().getUser().getName());
        String description = "O seu pedido N° "+ budgetCode +" foi entregue";

        firebaseService.sendNewEventByUser(EventForm.builder()
                .title(title)
                .message(description)
                .image("")
                .build(), budget.get().getUser());

    }

    public Page<BudgetView> findBudgetPageByUser(String authorization, Pageable pageable) {
        User user = authenticationService.getUser(authorization);
        log.info(">> findBudgetUser [user={}, pageable={}]", user.getEmail(), pageable);

        Page<Budget> page = repository.findByUser(user, pageable);
        log.info("<< findBudgetUser [page size={}]", page.getSize());

        List<BudgetView> views = getReservedByBudgetPage(page);

        return new PageImpl<>(new ArrayList<>(views));
    }

    public Page<Budget> findByBudgetStatusEnum(BudgetStatusEnum status, Pageable pageable) {
        return repository.findByBudgetStatusEnum(status, pageable);
    }

    public BudgetView findBudgetByUserAndById(String authorization, Long budgetId) {
        User user = authenticationService.getUser(authorization);
        log.info(">> findBudgetByCode [user={}, budgetId={}]", user.getEmail(), budgetId);

        Optional<Budget> budget = findByUserAndId(user, budgetId);
        log.info("<< findBudgetUser [budgetIsPresent={}]", budget.isPresent());

        if(!budget.isPresent()) {
            throw new GlobalException("Nenhum pedido encontrado");
        }

        BudgetView view = getReservedByBudget(budget.get());

        return view;
    }

    public Optional<Budget> findByUserAndId(User user, Long budgetId) {
        return repository.findByUserAndId(user, budgetId);
    }

    private List<BudgetView> getReservedByBudgetPage(Page<Budget> page) {
        List<BudgetView> views = page.getContent().stream().map(budgetMapper::entityToView)
                .collect(Collectors.toList());

        views.stream().forEach(budget -> {
            List<BudgetProductReserved> list = budgetProductReservedService.findByBudgetCode(budget.getId());
            budget.setProductReservedViewList(list.stream()
                    .map(budgetProductReservedMapper::entityToView).collect(Collectors.toList()));
        });
        return views;
    }

    private BudgetView getReservedByBudget(Budget budget) {
        BudgetView views = budgetMapper.entityToView(budget);
        List<BudgetProductReserved> list = budgetProductReservedService.findByBudget(budget);
        views.setProductReservedViewList(list.stream()
                .map(budgetProductReservedMapper::entityToView).collect(Collectors.toList()));
        return views;
    }

    public BigDecimal calculatedProduct(BudgetProductReserved budgetProductReserved, boolean isCupom) {
        Integer quantity = budgetProductReserved.getQuantity();
        if(budgetProductReserved.getProduct().isExistPercentage() && !isCupom) {
            BigDecimal valueAmount = budgetProductReserved.getProduct().getValue().multiply(new BigDecimal(quantity));
            BigDecimal valueDiscountAmount = budgetProductReserved.getProduct().getDiscountValue().multiply(new BigDecimal(quantity));
            return valueAmount.subtract(valueDiscountAmount);
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
            if(!inventory.isPresent() || (inventory.isPresent() && inventory.get().getQuantity() == 0)) {
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
