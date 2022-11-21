package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.ProductForm;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.models.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductMapperImpl implements MapStructMapper<Product, ProductView, ProductForm>{

    @Autowired
    private CategoryMapperImpl categoryMapper;

    @Autowired
    private ProductFileMapperImpl productFileMapper;

    @Override
    public ProductView entityToView(Product product) {
        return ProductView.builder()
                .id(product.getId())
                .categoryView(categoryMapper.entityToView(product.getCategory()))
                .productFileView(productFileMapper.entityToView(product.getProductFile()))
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .tag(product.getTag())
                .value(product.getValue())
                .discountValue(product.getDiscountValue())
                .percentageValue(product.getPercentageValue())
                .minimumOrder(product.getMinimumOrder())
                .existPercentage(product.isExistPercentage())
                .build();
    }

    @Override
    public Product formToEntity(ProductForm productForm) {
        return Product.builder()
                .id(productForm.getId())
                .category(categoryMapper.formToEntity(productForm.getCategoryForm()))
                .productFile(productFileMapper.formToEntity(productForm.getProductFileForm()))
                .code(productForm.getCode())
                .name(productForm.getName())
                .description(productForm.getDescription())
                .tag(productForm.getTag())
                .value(productForm.getValue())
                .discountValue(productForm.getDiscountValue())
                .percentageValue(productForm.getPercentageValue())
                .minimumOrder(productForm.getMinimumOrder())
                .existPercentage(productForm.getDiscountValue().compareTo(BigDecimal.ZERO) > 0)
                .build();
    }

    @Override
    public ProductForm viewToForm(ProductView productView) {
        return ProductForm.builder()
                .id(productView.getId())
                .code(productView.getCode())
                .name(productView.getName())
                .description(productView.getDescription())
                .tag(productView.getTag())
                .value(productView.getValue())
                .discountValue(productView.getDiscountValue())
                .percentageValue(productView.getPercentageValue())
                .minimumOrder(productView.getMinimumOrder())
                .build();
    }

    @Override
    public ProductForm entityToForm(Product product) {
        return ProductForm.builder()
                .id(product.getId())
                .categoryForm(categoryMapper.entityToForm(product.getCategory()))
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .tag(product.getTag())
                .value(product.getValue())
                .discountValue(product.getDiscountValue())
                .percentageValue(product.getPercentageValue())
                .minimumOrder(product.getMinimumOrder())
                .build();
    }
}
