package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CupomForm {
    private Long id;
    @NotNull(message = "Informe um usuário para este cupom")
    private UserForm user;
    @NotBlank(message = "Insira um código para o cupom")
    private String code;
    private String description;
    @JsonIgnore
    private Date dateCreated;
    @NotNull(message = "Insira uma data de expiração")
    private Date dateExpired;
    @NotNull(message = "Insira um status para o cupom")
    private CupomStatusEnum cupomStatusEnum;
    @NotNull(message = "Insira o valor do cupom")
    private BigDecimal cupomPercentage;
}
