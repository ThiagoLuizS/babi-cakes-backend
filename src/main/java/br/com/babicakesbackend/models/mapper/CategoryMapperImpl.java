package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.models.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperImpl implements MapStructMapper<Category, CategoryView, CategoryForm>{

    @Autowired
    private CategoryFileMapperImpl categoryFileMapper;
    @Override
    public CategoryView entityToView(Category category) {
        return CategoryView.builder()
                .id(category.getId())
                .categoryFileView(categoryFileMapper.entityToView(category.getCategoryFile()))
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public Category formToEntity(CategoryForm categoryForm) {
        return Category.builder()
                .id(categoryForm.getId())
                .categoryFile(categoryFileMapper.formToEntity(categoryForm.getCategoryFileForm()))
                .name(categoryForm.getName())
                .description(categoryForm.getDescription())
                .build();
    }

    @Override
    public CategoryForm viewToForm(CategoryView categoryView) {
        return null;
    }
}
