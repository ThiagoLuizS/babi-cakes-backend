package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.resource.CategoryResource;
import br.com.babicakesbackend.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Api(value = "Categorias de produtos")
public class CategoryController implements CategoryResource {

    private final CategoryService service;

    @Override
    public void save(CategoryForm categoryForm, MultipartFile file) throws Exception {
        service.saveCustom(categoryForm, file);
    }
}
