package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSimpleForm {
    private Long id;
    @NotBlank(message = "Informe seu nome")
    private String name;
    @NotBlank(message = "Informe seu email")
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Informe sua data de nascimento")
    private Date birthday;
    @NotBlank(message = "Informe um telefone celular válido")
    private String phone;
}
