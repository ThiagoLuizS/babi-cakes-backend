package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.dto.ProductFileForm;
import br.com.babicakesbackend.models.dto.ProductFileView;
import br.com.babicakesbackend.models.entity.CategoryFile;
import br.com.babicakesbackend.models.entity.ProductFile;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.ProductFileMapperImpl;
import br.com.babicakesbackend.repository.ProductFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductFileService extends AbstractService<ProductFile, ProductFileView, ProductFileForm>{

    private final ProductFileRepository repository;
    private final ProductFileMapperImpl productFileMapper;

    @Override
    protected JpaRepository<ProductFile, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<ProductFile, ProductFileView, ProductFileForm> getConverter() {
        return productFileMapper;
    }
}
