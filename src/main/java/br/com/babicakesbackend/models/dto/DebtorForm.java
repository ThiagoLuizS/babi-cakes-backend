package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebtorForm {

    @JsonAlias
    private String cnpj;

    @JsonAlias("nome")
    private String name;
}
