package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.dto.PropertyStringDTO;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import br.com.babicakesbackend.resource.BudgetResource;
import br.com.babicakesbackend.service.BudgetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Api(value = "Orçamentos ou pedidos")
public class BudgetController implements BudgetResource {

    private final BudgetService service;

    @Override
    public BudgetView createNewOrder(String authorization, String cupomCode, List<BudgetProductReservedForm> reservedForms) {
        return service.createNewBudget(authorization, cupomCode, reservedForms);
    }

    @Override
    public void canceledBudget(Long budgetCode) {
        service.canceledBudget(budgetCode);
    }

    @Override
    public Page<BudgetView> getBudgetPageByUser(String authorization, Pageable pageable) {
        return service.findBudgetPageByUser(authorization, pageable);
    }

    @Override
    public List<PropertyStringDTO> getBudgetStatus() {
        return service.findBudgetStatus();
    }

    @Override
    public Page<BudgetView> getBudgetPageAll(Long budgetCode, Long userName, BudgetStatusEnum budgetStatus, Date startDate, Date finalDate, Pageable pageable) {
        return service.findByPageAll(budgetCode, userName, budgetStatus, startDate, finalDate, pageable);
    }

    @Override
    public BudgetView getBudgetByUserAndById(String authorization, Long budgetId) {
        return service.findBudgetByUserAndById(authorization, budgetId);
    }

    @Override
    public void paidBudget(Long budgetCode) {
        service.paidBudget(budgetCode);
    }

    @Override
    public void preparingBudget(Long budgetCode) {
        service.preparingBudget(budgetCode);
    }

    @Override
    public void waitingForDelivery(Long budgetCode) {
        service.waitingForDelivery(budgetCode);
    }

    @Override
    public void budgetIsOutForDelivery(Long budgetCode) {
        service.budgetIsOutForDelivery(budgetCode);
    }

    @Override
    public void budgetDelivery(Long budgetCode) {
        service.budgetDelivery(budgetCode);
    }

}
