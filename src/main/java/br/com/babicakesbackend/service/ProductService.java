package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class ProductService extends AbstractService<Product, ProductView, ProductForm> {

    private final ProductRepository repository;
    private final ProductMapperImpl productMapper;
    private final ProductFileService productFileService;
    private final ProductFileMapperImpl productFileMapper;
    private final CategoryService categoryService;


    public void saveCustom(String productFormJson,  MultipartFile file) throws Exception {
        try {

            ProductForm productForm = new Gson().fromJson(productFormJson, ProductForm.class);

            if(Objects.nonNull(productForm.getPercentageValue())
                    && productForm.getPercentageValue().compareTo(BigDecimal.ZERO) > 0) {
                if(productForm.getPercentageValue().compareTo(new BigDecimal(100)) > 0) {
                    throw new GlobalException("Desconto por porcentagem maior que o permitido");
                } else {
                    BigDecimal discountValue = productForm.getValue().multiply(productForm.getPercentageValue());
                    discountValue = productForm.getValue().subtract(discountValue);
                    productForm.setDiscountValue(discountValue);
                }
            }

            CategoryForm categoryForm = categoryService.findCategoryFormById(productForm.getCategoryId());

            if(repository.existsByCode(productForm.getCode())) {
                throw new GlobalException("Código do produto já existe");
            }

            ProductFileForm productFileForm = ProductFileForm.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .photo(file.getBytes())
                    .build();

            ProductFileView fileView = productFileService.save(productFileForm);
            productFileForm = productFileMapper.viewToForm(fileView);
            productForm.setProductFileForm(productFileForm);
            productForm.setCategoryForm(categoryForm);

            save(productForm);

        } catch (Exception e) {
            log.error("<< Error [error={}]", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public Page<ProductView> findAllByCategoryId(Long categoryId, Pageable pageable, String productName) {
        log.info(">> findAllByCategoryId [categoryId={}, pageable={}]", categoryId, pageable);
        Page<Product> products = repository.findAllByCategoryIdAndNameStartsWithIgnoreCase(categoryId, productName, pageable);
        log.info("<< findAllByCategoryId [productsSize={}]", products.getContent().stream().count());
        List<ProductView> view = products.getContent().stream().map(getConverter()::entityToView).collect(Collectors.toList());
        return new PageImpl<>(view);
    }

    public Optional<Product> findById(Long id) {
        log.info(">> findById [id={}]", id);
        Optional<Product> product = repository.findById(id);
        log.info("<< findById [product isPresent={}]", product.isPresent());
        return product;
    }

    public Optional<Product> findByCode(Long code) {
        log.info(">> findByCode [code={}]", code);
        Optional<Product> product = repository.findByCode(code);
        log.info("<< findByCode [product isPresent={}]", product.isPresent());
        return product;
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
