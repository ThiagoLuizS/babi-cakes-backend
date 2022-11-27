package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.entity.Address;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetView {
    private Long id;
    private AddressView address;
    private Long code;
    private Date dateCreateBudget;
    private Date dateFinalizedBudget;
    private PropertyStringDTO budgetStatusEnum;
    List<BudgetProductReservedView> productReservedViewList;
    private BigDecimal amount;
    private BigDecimal freightCost;
}
