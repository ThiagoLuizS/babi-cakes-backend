package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.models.entity.Category;
import br.com.babicakesbackend.models.entity.CategoryFile;
import br.com.babicakesbackend.models.mapper.CategoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService extends AbstractService<Category, CategoryView, CategoryForm> {

    private final CategoryRepository repository;
    private final CategoryMapperImpl categoryMapper;

    public void saveCustom(CategoryForm categoryForm,  MultipartFile file) throws Exception {
        try {

            CategoryFileForm categoryFileForm = CategoryFileForm.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .photo(file.getBytes())
                    .build();

            categoryForm.setCategoryFileForm(categoryFileForm);

            save(categoryForm);

        } catch (Exception e) {
            throw new Exception("Falha ao salvar a categoria");
        }
    }

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Category, CategoryView, CategoryForm> getConverter() {
        return categoryMapper;
    }
}
