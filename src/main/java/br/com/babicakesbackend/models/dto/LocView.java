package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocView {

    private String id;

    private String location;

    @JsonProperty("tipoCob")
    private String typeCob;
}
