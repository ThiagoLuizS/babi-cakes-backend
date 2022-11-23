package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryForm {
    private Long id;
    @NotNull(message = "Informe um produto válido")
    private ProductForm product;
    @Min(value = 1, message = "A quantidade mínima é 1")
    @NotNull(message = "Informe uma quantidade")
    private Integer quantity;
}
