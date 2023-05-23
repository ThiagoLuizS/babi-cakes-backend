package br.com.babicakesbackend.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFileView {
    private Long id;
    private String name;
    private String type;
    @JsonIgnore
    private byte[] photo;
    private String photoBase64ToString;

    public String getPhotoBase64ToString() {
        return Base64.getEncoder().encodeToString(photo);
    }
}
