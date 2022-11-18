package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.entity.ProductFile;
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
    private ProductFileForm productFileForm;
    private Long code;
    private String name;
    private String description;
    private BigDecimal value;
    private BigDecimal discountValue;
    private BigDecimal percentageValue;
    private Integer minimumOrder;
    private boolean existPercentage;
    private String observation;
    private String tag;
}
