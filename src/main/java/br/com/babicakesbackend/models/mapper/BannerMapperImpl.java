package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.BannerForm;
import br.com.babicakesbackend.models.dto.BannerView;
import br.com.babicakesbackend.models.dto.CategoryView;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.models.entity.Banner;
import br.com.babicakesbackend.models.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BannerMapperImpl implements MapStructMapper<Banner, BannerView, BannerForm> {

    @Autowired
    private BannerFileMapperImpl bannerFileMapper;
    @Autowired
    private ProductMapperImpl productMapper;
    @Autowired
    private CategoryMapperImpl categoryMapper;

    @Override
    public BannerView entityToView(Banner banner) {
        return BannerView.builder()
                .id(banner.getId())
                .product(Objects.nonNull(banner.getProduct()) ? productMapper.entityToView(banner.getProduct()) : null)
                .category(Objects.nonNull(banner.getCategory()) ? categoryMapper.entityToView(banner.getCategory()) : null)
                .file(bannerFileMapper.entityToView(banner.getFile()))
                .title(banner.getTitle())
                .build();
    }

    @Override
    public Banner formToEntity(BannerForm bannerForm) {
        return Banner.builder()
                .id(bannerForm.getId())
                .product(productMapper.formToEntity(bannerForm.getProduct()))
                .category(categoryMapper.formToEntity(bannerForm.getCategory()))
                .file(bannerFileMapper.formToEntity(bannerForm.getFile()))
                .title(bannerForm.getTitle())
                .build();
    }

    @Override
    public BannerForm viewToForm(BannerView bannerView) {
        return BannerForm.builder()
                .id(bannerView.getId())
                .product(productMapper.viewToForm(bannerView.getProduct()))
                .category(categoryMapper.viewToForm(bannerView.getCategory()))
                .file(bannerFileMapper.viewToForm(bannerView.getFile()))
                .title(bannerView.getTitle())
                .build();
    }

    @Override
    public BannerForm entityToForm(Banner banner) {
        return BannerForm.builder()
                .id(banner.getId())
                .product(productMapper.entityToForm(banner.getProduct()))
                .category(categoryMapper.entityToForm(banner.getCategory()))
                .file(bannerFileMapper.entityToForm(banner.getFile()))
                .title(banner.getTitle())
                .build();
    }

    @Override
    public Banner viewToEntity(BannerView bannerView) {
        return null;
    }
}
