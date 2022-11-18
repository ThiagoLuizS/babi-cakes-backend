package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.ProductFileForm;
import br.com.babicakesbackend.models.dto.ProductFileView;
import br.com.babicakesbackend.models.dto.ProductForm;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.ProductFileMapperImpl;
import br.com.babicakesbackend.models.mapper.ProductMapperImpl;
import br.com.babicakesbackend.repository.ProductRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ProductService extends AbstractService<Product, ProductView, ProductForm> {

    private final ProductRepository repository;
    private final ProductMapperImpl productMapper;

    private final ProductFileService productFileService;

    private final ProductFileMapperImpl productFileMapper;

    public void saveCustom(String productFormJson,  MultipartFile file) throws Exception {
        try {

            ProductForm productForm = new Gson().fromJson(productFormJson, ProductForm.class);

            ProductFileForm productFileForm = ProductFileForm.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .photo(file.getBytes())
                    .build();

            ProductFileView fileView = productFileService.save(productFileForm);
            productFileForm = productFileMapper.viewToForm(fileView);
            productForm.setProductFileForm(productFileForm);
            save(productForm);

        } catch (Exception e) {
            log.error("<< Error [error={}]", e.getMessage());
            throw new Exception("Falha ao salvar a categoria");
        }
    }

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Product, ProductView, ProductForm> getConverter() {
        return productMapper;
    }
}
