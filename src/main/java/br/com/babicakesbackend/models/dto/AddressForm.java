package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressForm {
    private Long id;
    @NotNull(message = "Informe o cep")
    private Integer cep;
    @NotBlank(message = "Informe o tipo de endereço")
    @JsonProperty("address_type")
    private String addressType;
    @NotBlank(message = "Informe o nome do endereço")
    @JsonProperty("address_name")
    private String addressName;
    @NotBlank(message = "Informe o estado")
    private String state;
    @NotBlank(message = "Informe o bairro")
    private String district;
    private String lat;
    private String lng;
    @NotBlank(message = "Informe a cidade")
    private String city;
    @JsonProperty("city_ibge")
    private String cityIbge;
    private String ddd;
    @NotBlank(message = "Informe o número")
    private String number;
    private String complement;
}
