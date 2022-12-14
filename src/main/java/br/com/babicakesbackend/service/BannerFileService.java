package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.BannerFileForm;
import br.com.babicakesbackend.models.dto.BannerFileView;
import br.com.babicakesbackend.models.entity.BannerFile;
import br.com.babicakesbackend.models.mapper.BannerFileMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.BannerFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerFileService extends AbstractService<BannerFile, BannerFileView, BannerFileForm> {

    private final BannerFileRepository repository;
    private final BannerFileMapperImpl bannerFileMapper;

    @Override
    protected JpaRepository<BannerFile, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<BannerFile, BannerFileView, BannerFileForm> getConverter() {
        return bannerFileMapper;
    }
}
