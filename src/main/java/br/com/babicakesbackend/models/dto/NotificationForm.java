package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationForm {
    @NotBlank(message = "Informe um titulo para mensagem")
    private String title;
    @NotBlank(message = "Informe o corpo da mensagem")
    private String message;
}
