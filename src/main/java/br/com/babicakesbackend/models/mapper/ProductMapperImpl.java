package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.ProductForm;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.models.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapperImpl implements MapStructMapper<Product, ProductView, ProductForm>{

    @Autowired
    private CategoryMapperImpl categoryMapper;

    @Override
    public ProductView entityToView(Product product) {
        return ProductView.builder()
                .id(product.getId())
                .categoryView(categoryMapper.entityToView(product.getCategory()))
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .tag(product.getTag())
                .value(product.getValue())
                .discountValue(product.getDiscountValue())
                .percentageValue(product.getPercentageValue())
                .minimumOrder(product.getMinimumOrder())
                .existPercentage(product.isExistPercentage())
                .observation(product.getObservation())
                .build();
    }

    @Override
    public Product formToEntity(ProductForm productForm) {
        return Product.builder()
                .id(productForm.getId())
                .code(productForm.getCode())
                .name(productForm.getName())
                .description(productForm.getDescription())
                .tag(productForm.getTag())
                .value(productForm.getValue())
                .discountValue(productForm.getDiscountValue())
                .percentageValue(productForm.getPercentageValue())
                .minimumOrder(productForm.getMinimumOrder())
                .existPercentage(productForm.isExistPercentage())
                .observation(productForm.getObservation())
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
                .existPercentage(productView.isExistPercentage())
                .observation(productView.getObservation())
                .build();
    }
}
