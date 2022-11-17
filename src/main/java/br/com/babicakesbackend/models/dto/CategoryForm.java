package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryForm {
    private Long id;
    private CategoryFileForm categoryFileForm;
    private String name;
    private String description;
}
