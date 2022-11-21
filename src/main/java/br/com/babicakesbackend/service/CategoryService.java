package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.models.entity.Category;
import br.com.babicakesbackend.models.entity.CategoryFile;
import br.com.babicakesbackend.models.mapper.CategoryFileMapperImpl;
import br.com.babicakesbackend.models.mapper.CategoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.CategoryRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CategoryService extends AbstractService<Category, CategoryView, CategoryForm> {

    private final CategoryRepository repository;
    private final CategoryMapperImpl categoryMapper;
    private final CategoryFileMapperImpl categoryFileMapper;
    private final CategoryFileService categoryFileService;

    public void saveCustom(String categoryFormJson,  MultipartFile file) throws Exception {
        try {

            CategoryForm categoryForm = new Gson().fromJson(categoryFormJson, CategoryForm.class);

            CategoryFileForm categoryFileForm = CategoryFileForm.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .photo(file.getBytes())
                    .build();

            CategoryFileView fileView = categoryFileService.save(categoryFileForm);
            categoryFileForm = categoryFileMapper.viewToForm(fileView);
            categoryForm.setCategoryFileForm(categoryFileForm);
            save(categoryForm);

        } catch (Exception e) {
            log.error("<< Error [error={}]", e.getMessage());
            throw new Exception("Falha ao salvar a categoria");
        }
    }

    public CategoryForm findCategoryFormById(Long id) {
        log.info(">> findCategoryFormById [id={}]", id);
        Optional<Category> category = repository.findById(id);
        log.info("<< findCategoryFormById [data={}]", category.get().getId());
        return categoryMapper.entityToForm(category.get());
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