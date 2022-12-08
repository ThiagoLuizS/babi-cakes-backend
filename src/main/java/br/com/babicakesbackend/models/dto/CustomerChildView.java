package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerChildView {
    private String name;
    private String email;
    private String phone;
    private TaxChildView taxID;
    private String correlationID;
}
