package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.BudgetForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.entity.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class BudgetMapperImpl implements MapStructMapper<Budget, BudgetView, BudgetForm> {

    @Autowired
    private AddressMapperImpl addressMapper;

    @Autowired
    private CupomMapperImpl cupomMapper;

    @Autowired
    private UserMapperImpl userMapper;

    private BudgetProductReservedMapperImpl productReservedMapper;

    @Override
    public BudgetView entityToView(Budget budget) {
        return BudgetView.builder()
                .id(budget.getId())
                .address(addressMapper.entityToView(budget.getAddress()))
                .cupom(Objects.nonNull(budget.getCupom()) ? cupomMapper.entityToView(budget.getCupom()) : null)
                .user(userMapper.entityToView(budget.getUser()))
                .code(budget.getCode())
                .dateCreateBudget(budget.getDateCreateBudget())
                .dateFinalizedBudget(budget.getDateFinalizedBudget())
                .budgetStatusEnum(budget.getBudgetStatusEnum().getProperty())
                .freightCost(budget.getFreightCost())
                .subTotal(budget.getSubTotal())
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

    @Override
    public Budget viewToEntity(BudgetView budgetView) {
        return null;
    }
}
