package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeForm {
    private String correlationID;
    private BigDecimal value;
    private String comment;
    private CustomerForm customer;
    private List<AdditionalInfo> additionalInfo;
}
