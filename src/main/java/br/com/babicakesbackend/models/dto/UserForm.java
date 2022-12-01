package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    private Long id;
    @NotNull(message = "Informe seu nome")
    private String name;
    @NotNull(message = "Informe seu email")
    private String email;
    @Size(min = 6, max = 8, message = "A senha deve ter 6 digitos")
    @NotNull(message = "Informe uma senha")
    private String password;
    @NotNull(message = "Informe sua data de nascimento")
    private Date birthday;
    @NotNull(message = "Informe um telefone celular válido")
    private String phone;
    @JsonIgnore
    private UserStatusEnum status = UserStatusEnum.ACTIVE;
}
