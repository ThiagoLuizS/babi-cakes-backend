package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.entity.CategoryFile;
import br.com.babicakesbackend.models.mapper.CategoryFileMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.CategoryFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryFileService extends AbstractService<CategoryFile, CategoryFileView, CategoryFileForm> {

    private final CategoryFileRepository repository;

    private final CategoryFileMapperImpl categoryFileMapperer;

    @Override
    protected JpaRepository<CategoryFile, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<CategoryFile, CategoryFileView, CategoryFileForm> getConverter() {
        return categoryFileMapperer;
    }
}
