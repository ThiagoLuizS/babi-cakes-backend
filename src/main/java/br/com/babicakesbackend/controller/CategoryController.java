package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.resource.CategoryResource;
import br.com.babicakesbackend.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Api(value = "Categorias de produtos")
public class CategoryController implements CategoryResource {

    private final CategoryService service;

    @Override
    public CategoryView save(String categoryFormJson, MultipartFile file) throws Exception {
        return service.saveCustom(categoryFormJson, file);
    }

    @Override
    public Page<CategoryView> getAllByPage(List<Boolean> excluded, Pageable pageable) {
        return service.findAll(pageable, excluded);
    }

    @Override
    public Page<CategoryView> getAllFilterByPage(String categoryName, List<Boolean> excluded, Pageable pageable) {
        return service.findAllFilter(pageable, categoryName, excluded);
    }

    @Override
    public void inactivateCategory(Long id) {
        service.hideCategory(id);
    }

    @Override
    public void reactivateCategory(Long id) {
        service.showCategory(id);
    }
}
