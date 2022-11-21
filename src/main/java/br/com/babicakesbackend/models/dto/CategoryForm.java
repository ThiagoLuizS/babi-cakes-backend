package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryForm {
    private Long id;
    private CategoryFileForm categoryFileForm;
    private String name;
    private String description;
    private boolean show;
}
