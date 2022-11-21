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
public class ProductView {
    private Long id;
    private CategoryView categoryView;
    private ProductFileView productFileView;
    private Long code;
    private String name;
    private String description;
    private BigDecimal value;
    private BigDecimal discountValue;
    private BigDecimal percentageValue;
    private Integer minimumOrder;
    private boolean existPercentage;
    private String tag;
}
