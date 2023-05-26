package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterizationView {
    private Long id;
    private BigDecimal freightCost = BigDecimal.ZERO;
    private boolean openShop;
    private String linkWhatsapp;
}
