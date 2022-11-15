package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotBlank(message = "Email obrigatório!")
    private String email;
    @NotBlank(message = "Senha obrigatória!")
    private String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
