package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetProductReservedView;
import br.com.babicakesbackend.models.entity.BudgetProductReserved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BudgetProductReservedMapperImpl implements
        MapStructMapper<BudgetProductReserved, BudgetProductReservedView, BudgetProductReservedForm> {

    @Autowired
    private ProductMapperImpl productMapper;

    @Override
    public BudgetProductReservedView entityToView(BudgetProductReserved budgetProductReserved) {
        return BudgetProductReservedView.builder()
                .id(budgetProductReserved.getId())
                .productView(productMapper.entityToView(budgetProductReserved.getProduct()))
                .quantity(budgetProductReserved.getQuantity())
                .build();
    }

    @Override
    public BudgetProductReserved formToEntity(BudgetProductReservedForm budgetProductReservedForm) {
        return BudgetProductReserved.builder()
                .id(budgetProductReservedForm.getId())
                .quantity(budgetProductReservedForm.getQuantity())
                .build();
    }

    @Override
    public BudgetProductReservedForm viewToForm(BudgetProductReservedView budgetProductReservedView) {
        return BudgetProductReservedForm.builder()
                .id(budgetProductReservedView.getId())
                .quantity(budgetProductReservedView.getQuantity())
                .build();
    }

    @Override
    public BudgetProductReservedForm entityToForm(BudgetProductReserved budgetProductReserved) {
        return BudgetProductReservedForm.builder()
                .id(budgetProductReserved.getId())
                .quantity(budgetProductReserved.getQuantity())
                .build();
    }
}
