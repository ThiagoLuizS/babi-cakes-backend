package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.resource.ProductResource;
import br.com.babicakesbackend.service.ProductService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Api(value = "Produtos")
public class ProductController implements ProductResource {

    private final ProductService service;

    @Override
    public ProductView save(String productFormJson, MultipartFile file) throws Exception {
        return service.saveCustom(productFormJson, file);
    }

    @Override
    public ProductView update(Long id, String productFormJson, MultipartFile file) throws Exception {
        return service.updateCustom(id, productFormJson, file);
    }

    @Override
    public void inactivateProduct(Long id) {
        service.inactivateProduct(id);
    }

    @Override
    public void reactivateProduct(Long id) {
        service.reactivateProduct(id);
    }

    @Override
    public Page<ProductView> getAllByPageAndCategory(Pageable pageable, Long categoryId, String productName, List<Boolean> show) {
        return service.findAllByCategoryId(categoryId, pageable, productName, show);
    }

    @Override
    public Page<ProductView> getAllByPage(Pageable pageable, String productName, List<Boolean> show) {
        return service.findAll(pageable, productName, show);
    }
}
