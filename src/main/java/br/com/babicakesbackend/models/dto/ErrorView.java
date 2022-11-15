package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorView {

    private LocalDateTime timestamp = LocalDateTime.now();
    private String path;
    private Integer status;
    private String error;
    private List<String> messages;
}
