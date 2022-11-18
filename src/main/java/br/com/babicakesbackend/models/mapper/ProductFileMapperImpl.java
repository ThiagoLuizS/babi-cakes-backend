package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.ProductFileForm;
import br.com.babicakesbackend.models.dto.ProductFileView;
import br.com.babicakesbackend.models.entity.ProductFile;
import org.springframework.stereotype.Component;

@Component
public class ProductFileMapperImpl implements MapStructMapper<ProductFile, ProductFileView, ProductFileForm> {
    @Override
    public ProductFileView entityToView(ProductFile productFile) {
        return ProductFileView.builder()
                .id(productFile.getId())
                .name(productFile.getName())
                .type(productFile.getType())
                .photo(productFile.getPhoto())
                .build();
    }

    @Override
    public ProductFile formToEntity(ProductFileForm productFileForm) {
        return ProductFile.builder()
                .id(productFileForm.getId())
                .name(productFileForm.getName())
                .type(productFileForm.getType())
                .photo(productFileForm.getPhoto())
                .build();
    }

    @Override
    public ProductFileForm viewToForm(ProductFileView productFileView) {
        return ProductFileForm.builder()
                .id(productFileView.getId())
                .name(productFileView.getName())
                .type(productFileView.getType())
                .photo(productFileView.getPhoto())
                .build();
    }
}
