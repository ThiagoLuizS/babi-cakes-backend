package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.models.entity.Category;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.mapper.CategoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.CategoryRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class CategoryService extends AbstractService<Category, CategoryView, CategoryForm> {

    private final CategoryRepository repository;
    private final CategoryMapperImpl categoryMapper;
    private final CategoryFileService categoryFileService;

    public CategoryView saveCustom(String categoryFormJson,  MultipartFile file) throws Exception {
        try {

            CategoryForm categoryForm = new Gson().fromJson(categoryFormJson, CategoryForm.class);

            CategoryFileForm categoryFileForm = CategoryFileForm.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .photo(file.getBytes())
                    .build();

            CategoryFileView fileView = categoryFileService.save(categoryFileForm);
            categoryFileForm = categoryFileService.getConverter().viewToForm(fileView);
            categoryForm.setCategoryFileForm(categoryFileForm);
            return save(categoryForm);
        } catch (Exception e) {
            log.error("<< Error [error={}]", e.getMessage());
            throw new GlobalException("Falha ao salvar a categoria");
        }
    }

    public void hideCategory(Long id) {
        Optional<Category> category = repository.findById(id);
        if(!category.isPresent()) {
            throw new GlobalException("Categoria informada não existe");
        }
        category.get().setShow(false);

        repository.save(category.get());
    }

    public void showCategory(Long id) {
        Optional<Category> category = repository.findById(id);
        if(!category.isPresent()) {
            throw new GlobalException("Categoria informada não existe");
        }
        category.get().setShow(true);

        repository.save(category.get());
    }

    public CategoryForm findCategoryFormById(Long id) {
        log.info(">> findCategoryFormById [id={}]", id);
        Optional<Category> category = repository.findById(id);
        if(!category.isPresent()) {
            throw new GlobalException("Categoria informada não existe");
        }
        log.info("<< findCategoryFormById [data={}]", category.get().getId());
        return categoryMapper.entityToForm(category.get());
    }

    public Page<CategoryView> findAll(Pageable pageable, List<Boolean> show) {
        log.info(">> findAll [show={}, pageable={}]", show, pageable);
        Page<Category> categories = repository.findAllByShowIn(show, pageable);
        log.info("<< findAll [categoriesSize={}]", categories.getContent().stream().count());
        List<CategoryView> view = categories.getContent().stream().map(getConverter()::entityToView).collect(Collectors.toList());
        return new PageImpl<>(view);
    }

    public Page<CategoryView> findAllFilter(Pageable pageable, String categoryName, List<Boolean> show) {
        log.info(">> findAll [excluded={}, pageable={}]", show, pageable);
        Page<Category> categories = repository.findAllByNameStartsWithIgnoreCaseAndShowIn(categoryName, show, pageable);
        log.info("<< findAll [categoriesSize={}]", categories.getContent().stream().count());
        List<CategoryView> view = categories.getContent().stream().map(getConverter()::entityToView).collect(Collectors.toList());
        return new PageImpl<>(view);
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
