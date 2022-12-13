package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.NotificationForm;
import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.Charge;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import br.com.babicakesbackend.models.enumerators.PixStatusEnum;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckPaymentsService {

    private final BudgetService budgetService;
    private final ChargeService chargeService;
    private final TemplatePixService pixService;

    private final FirebaseService firebaseService;

    @Value("${pageable.request.size}")
    private Integer pageSize;

    public void checkPaymentProcess() {
        log.info(">> ================ checkPaymentProcess ================");
        try {
            Page<Charge> page = chargeService.findByStatus(PixStatusEnum.ACTIVE, Pageable.ofSize(pageSize));

            page.getContent().stream().forEach(charge -> {
                ChargeView form = pixService.getImmediateChargeNoAction(charge.getTransactionID());

                if(Objects.nonNull(form) && form.getCharge().getStatus().equals(PixStatusEnum.COMPLETED)) {
                    charge.setStatus(form.getCharge().getStatus());
                    charge.getBudget().setDateFinalizedBudget(new Date());
                    charge.getBudget().setBudgetStatusEnum(BudgetStatusEnum.PAID_OUT);
                    budgetService.saveCustom(charge.getBudget());
                    chargeService.saveCustom(charge);

                    firebaseService.sendNewEventByUser(EventForm.builder()
                                    .title("PIX: " + PixStatusEnum.COMPLETED.name())
                                    .message("BUDGET: " + BudgetStatusEnum.PAID_OUT.name())
                                    .image("")
                            .build(), charge.getBudget().getUser());
                    firebaseService.sendNotificationByUser(NotificationForm.builder()
                                    .title(ConstantUtils.getFirstName(charge.getBudget().getUser().getName()))
                                    .message("Recebemos seu PIX, estamos preparando sua encomenda!")
                            .build(), charge.getBudget().getUser().getId());
                }
            });
            log.info("<< ================ checkPaymentProcess [pageContentSize={}] ================", page.getContent().size());
        } catch (Exception e) {
            log.error("<< ================ checkPaymentProcess [error={}] ================", e.getMessage());
        }


    }


}
