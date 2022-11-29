package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventForm {
    @NotBlank(message = "Informe um titulo para mensagem")
    private String title;
    @NotBlank(message = "Informe o corpo da mensagem")
    private String description;
    private String image = "";
    private List<User> users;
    private boolean available = true;
}
