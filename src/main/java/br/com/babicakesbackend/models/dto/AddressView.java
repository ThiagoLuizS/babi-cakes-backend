package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressView {
    private Long id;
    private Integer cep;
    private boolean addressMain;
    private String addressType;
    private String addressName;
    private String state;
    private String district;
    private String lat;
    private String lng;
    private String city;
    private String cityIbge;
    private String ddd;
    private String number;
    private String complement;
}
