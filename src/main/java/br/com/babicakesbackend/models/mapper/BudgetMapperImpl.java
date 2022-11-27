package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.BudgetForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.entity.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BudgetMapperImpl implements MapStructMapper<Budget, BudgetView, BudgetForm> {

    @Autowired
    private AddressMapperImpl addressMapper;

    @Override
    public BudgetView entityToView(Budget budget) {
        return BudgetView.builder()
                .id(budget.getId())
                .address(addressMapper.entityToView(budget.getAddress()))
                .code(budget.getCode())
                .dateCreateBudget(budget.getDateCreateBudget())
                .dateFinalizedBudget(budget.getDateFinalizedBudget())
                .budgetStatusEnum(budget.getBudgetStatusEnum().getProperty())
                .freightCost(budget.getFreightCost())
                .amount(budget.getAmount())
                .build();
    }

    @Override
    public Budget formToEntity(BudgetForm budgetForm) {
        return Budget.builder()
                .id(budgetForm.getId())
                .code(budgetForm.getCode())
                .build();
    }

    @Override
    public BudgetForm viewToForm(BudgetView budgetView) {
        return BudgetForm.builder()
                .id(budgetView.getId())
                .code(budgetView.getCode())
                .build();
    }

    @Override
    public BudgetForm entityToForm(Budget budget) {
        return BudgetForm.builder()
                .id(budget.getId())
                .code(budget.getCode())
                .build();
    }
}
