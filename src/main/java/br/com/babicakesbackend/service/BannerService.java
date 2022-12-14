package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.BannerFileForm;
import br.com.babicakesbackend.models.dto.BannerFileView;
import br.com.babicakesbackend.models.dto.BannerForm;
import br.com.babicakesbackend.models.dto.BannerView;
import br.com.babicakesbackend.models.dto.CategoryFileForm;
import br.com.babicakesbackend.models.dto.CategoryFileView;
import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.entity.Banner;
import br.com.babicakesbackend.models.entity.BannerFile;
import br.com.babicakesbackend.models.entity.Category;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.mapper.BannerMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.BannerRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = GlobalException.class)
public class BannerService extends AbstractService<Banner, BannerView, BannerForm> {

    private final BannerRepository repository;
    private final BannerMapperImpl bannerMapper;
    private final BannerFileService bannerFileService;
    private final ProductService productService;
    private final CategoryService categoryService;

    public void saveCustom(String bannerFormJson,  MultipartFile file) throws Exception {
        try {
            Optional<Product> product = Optional.empty();
            Optional<Category> category = Optional.empty();

            BannerForm bannerForm = new Gson().fromJson(bannerFormJson, BannerForm.class);

            if(Objects.nonNull(bannerForm.getProduct()) && Objects.nonNull(bannerForm.getCategory())) {
                throw new GlobalException("Não é possivel selecionar um produto e uma categoria para o banner, informe apenas uma opção");
            } else if(Objects.isNull(bannerForm.getProduct()) && Objects.isNull(bannerForm.getCategory())) {
                throw new GlobalException("É necessário informar um produto ou uma categoria");
            } else if(StringUtils.isBlank(bannerForm.getTitle())) {
                throw new GlobalException("Informe um titulo para o banner");
            }

            if(Objects.nonNull(bannerForm.getProduct())) {
                product = productService.findEntityById(bannerForm.getProduct().getId());
            } else if(Objects.nonNull(bannerForm.getCategory()))  {
                category = categoryService.findEntityById(bannerForm.getCategory().getId());
            }

            if(Objects.nonNull(bannerForm.getProduct()) && !product.isPresent()) {
                throw new GlobalException("O produto informado não existe");
            } else if(Objects.nonNull(bannerForm.getCategory()) && !category.isPresent()) {
                throw new GlobalException("A categoria informada não existe");
            }

            BannerFileForm bannerFileForm = BannerFileForm.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .photo(file.getBytes())
                    .build();

            BannerFile bannerFile = bannerFileService.saveToEntity(bannerFileForm);

            Banner banner = Banner.builder()
                    .file(bannerFile)
                    .title(bannerForm.getTitle())
                    .build();

            if(product.isPresent()) {
                banner.setProduct(product.get());
            } else {
                banner.setCategory(category.get());
            }

            repository.save(banner);

        } catch (Exception e) {
            log.error("<< Error [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }


    @Override
    protected JpaRepository<Banner, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Banner, BannerView, BannerForm> getConverter() {
        return bannerMapper;
    }
}
