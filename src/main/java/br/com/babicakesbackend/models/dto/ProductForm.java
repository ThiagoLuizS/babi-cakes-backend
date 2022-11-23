package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.entity.ProductFile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private Long id;
    private Long categoryId;
    @JsonIgnore
    private CategoryForm categoryForm;
    @JsonIgnore
    private ProductFileForm productFileForm;
    private Long code;
    private String name;
    private String description;
    private BigDecimal value = BigDecimal.ZERO;
    @JsonIgnore
    private BigDecimal discountValue = BigDecimal.ZERO;
    private BigDecimal percentageValue = BigDecimal.ZERO;
    private Integer minimumOrder;
    private String tag;
    private boolean withStock;
}
