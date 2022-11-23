package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.resource.BudgetResource;
import br.com.babicakesbackend.service.BudgetService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
@Api(value = "Orçamentos ou pedidos")
public class BudgetController implements BudgetResource {

    private final BudgetService service;

    @Override
    public void createNewOrder(String authorization, String cupomCode, List<BudgetProductReservedForm> reservedForms) {
        service.createNewBudget(authorization, cupomCode, reservedForms);
    }

    @Override
    public void canceledBudget(Long budgetCode) {
        service.canceledBudget(budgetCode);
    }

    @Override
    public Page<BudgetView> getBudgetPageByUser(String authorization, Pageable pageable) {
        return service.findBudgetPageByUser(authorization, pageable);
    }

}
