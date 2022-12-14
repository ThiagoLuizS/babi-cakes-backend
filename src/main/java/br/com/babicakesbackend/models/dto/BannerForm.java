package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.entity.BannerFile;
import br.com.babicakesbackend.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerForm {
    private Long id;
    private ProductForm product;
    private CategoryForm category;
    private BannerFileForm file;
    @NotBlank
    private String title;
}
