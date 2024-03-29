package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BudgetProductReservedForm {
    @JsonIgnore
    private Long id;
    private Long productCode;
    private Integer quantity;
}
