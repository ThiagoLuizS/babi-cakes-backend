package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.AdditionalInfo;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.models.dto.CustomerForm;
import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.Charge;
import br.com.babicakesbackend.models.entity.Parameterization;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import br.com.babicakesbackend.models.enumerators.PixStatusEnum;
import br.com.babicakesbackend.models.mapper.ChargeMapperImpl;
import br.com.babicakesbackend.repository.ChargeRepository;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = GlobalException.class)
public class ChargeService {

    private final BudgetService budgetService;
    private final ChargeRepository chargeRepository;
    private final ChargeMapperImpl chargeMapper;
    private final ParameterizationService parameterizationService;
    private final TemplatePixService pixService;
    private final AuthenticationService authenticationService;

    public Charge createImmediateCharge(String authorization, Long budgetId) {
        try {

            BudgetView budgetView = budgetService.findBudgetByUserAndById(authorization, budgetId);

            List<Charge> charges = chargeRepository.findByBudgetId(budgetView.getId());

            Optional<Charge> chargeExist = validChargeActiveExists(budgetView, charges);

            if(Objects.equals(BudgetStatusEnum.valueOf(budgetView.getBudgetStatusEnum().getType()), BudgetStatusEnum.AWAITING_PAYMENT)) {

                if(!chargeExist.isPresent()) {
                    ChargeForm chargeForm = getChargeForm(budgetView);

                    ChargeView chargeView = pixService.createImmediateCharge(chargeForm);

                    Charge charge = chargeMapper.viewToEntity(chargeView);

                    charge.setBudget(Budget.builder().id(budgetView.getId()).build());

                    chargeRepository.save(charge);

                    return charge;
                }

               return chargeExist.get();

            } else {
                throw new GlobalException("O pedido não está aguardando um pagamento");
            }

        } catch (Exception e) {
            log.error("<< createImmediateCharge [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    private Optional<Charge> validChargeActiveExists(BudgetView budgetView, List<Charge> charges) {
        charges.stream().forEach(charge -> {
            if(charge.getStatus().equals(PixStatusEnum.COMPLETED) && charge.getValue().compareTo(budgetView.getAmount()) == 0) {
                throw new GlobalException("O pedido de N° " + budgetView.getCode() + " já foi pago");
            }
        });

        List<Charge> first = charges.stream().filter(ft -> ft.getStatus().equals(PixStatusEnum.ACTIVE)
                        && new Date().compareTo(ft.getExpiresDate()) <= 0)
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(first)) {
            return Optional.empty();
        }

        return first.stream().findFirst();
    }

    public Charge saveCustom(Charge charge) {
        return chargeRepository.save(charge);
    }

    private static ChargeForm getChargeForm(BudgetView budgetView) {
        return ChargeForm.builder()
                .correlationID(ConstantUtils.formatToShar(ConstantUtils.generatedRandomNumber(32)))
                .value(budgetView.getAmount().compareTo(BigDecimal.TEN) < 0 ? ConstantUtils.formatBigCentsToInt(budgetView.getAmount()) : budgetView.getAmount())
                .comment("Pedido Nº " + budgetView.getCode())
                .customer(CustomerForm.builder()
                        .name(budgetView.getUser().getName())
                        .taxID("12178265642")
                        .email(budgetView.getUser().getEmail())
                        .phone(budgetView.getUser().getPhone())
                        .build())
                .additionalInfo(budgetView.getProductReservedViewList()
                        .stream()
                        .map(item -> AdditionalInfo.builder()
                                .key(item.getProductView().getName())
                                .value(item.getQuantity().toString())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Page<Charge> findByStatus(PixStatusEnum status, Pageable pageable) {
        return chargeRepository.findByStatus(status, pageable);
    }


}
