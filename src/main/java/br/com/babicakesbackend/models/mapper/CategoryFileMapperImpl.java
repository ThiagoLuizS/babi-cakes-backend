package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.entity.CategoryFile;
import org.springframework.stereotype.Component;

@Component
public class CategoryFileMapperImpl implements MapStructMapper<CategoryFile, CategoryFileView, CategoryFileForm>{
    @Override
    public CategoryFileView entityToView(CategoryFile categoryFile) {
        return CategoryFileView.builder()
                .id(categoryFile.getId())
                .name(categoryFile.getName())
                .type(categoryFile.getType())
                .photo(categoryFile.getPhoto())
                .build();
    }

    @Override
    public CategoryFile formToEntity(CategoryFileForm categoryFileForm) {
        return CategoryFile.builder()
                .id(categoryFileForm.getId())
                .name(categoryFileForm.getName())
                .type(categoryFileForm.getType())
                .photo(categoryFileForm.getPhoto())
                .build();
    }

    @Override
    public CategoryFileForm viewToForm(CategoryFileView categoryFileView) {
        return CategoryFileForm.builder()
                .id(categoryFileView.getId())
                .name(categoryFileView.getName())
                .type(categoryFileView.getType())
                .photo(categoryFileView.getPhoto())
                .build();
    }
}
