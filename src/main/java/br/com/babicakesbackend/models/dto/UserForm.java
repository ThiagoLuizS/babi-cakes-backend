package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private Long id;
    @NotBlank(message = "Informe seu nome")
    private String name;
    @NotBlank(message = "Informe seu email")
    private String email;
    @Size(min = 6, max = 8, message = "A senha deve ter no mínimo 6 e máximo 8 digitos")
    @NotBlank(message = "Informe uma senha")
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "Informe sua data de nascimento")
    private Date birthday;
    @NotBlank(message = "Informe um telefone celular válido")
    private String phone;
    @JsonIgnore
    private UserStatusEnum status = UserStatusEnum.ACTIVE;
}
