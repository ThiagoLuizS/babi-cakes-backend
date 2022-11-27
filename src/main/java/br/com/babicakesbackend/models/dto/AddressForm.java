package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressForm {
    private Long id;
    private Integer cep;
    @JsonProperty("address_type")
    private String addressType;
    @JsonProperty("address_name")
    private String addressName;
    private String state;
    private String district;
    private String lat;
    private String lng;
    private String city;
    @JsonProperty("city_ibge")
    private String cityIbge;
    private String ddd;
    private String number;
    private String complement;
}
