package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.BannerFileForm;
import br.com.babicakesbackend.models.dto.BannerFileView;
import br.com.babicakesbackend.models.entity.BannerFile;
import org.springframework.stereotype.Component;

@Component
public class BannerFileMapperImpl implements MapStructMapper<BannerFile, BannerFileView, BannerFileForm> {

    @Override
    public BannerFileView entityToView(BannerFile bannerFile) {
        return BannerFileView.builder()
                .id(bannerFile.getId())
                .name(bannerFile.getName())
                .type(bannerFile.getType())
                .photo(bannerFile.getPhoto())
                .build();
    }

    @Override
    public BannerFile formToEntity(BannerFileForm bannerFileForm) {
        return BannerFile.builder()
                .id(bannerFileForm.getId())
                .name(bannerFileForm.getName())
                .type(bannerFileForm.getType())
                .photo(bannerFileForm.getPhoto())
                .build();
    }

    @Override
    public BannerFileForm viewToForm(BannerFileView bannerFileView) {
        return BannerFileForm.builder()
                .id(bannerFileView.getId())
                .name(bannerFileView.getName())
                .type(bannerFileView.getType())
                .photo(bannerFileView.getPhoto())
                .build();
    }

    @Override
    public BannerFileForm entityToForm(BannerFile bannerFile) {
        return BannerFileForm.builder()
                .id(bannerFile.getId())
                .name(bannerFile.getName())
                .type(bannerFile.getType())
                .photo(bannerFile.getPhoto())
                .build();
    }

    @Override
    public BannerFile viewToEntity(BannerFileView bannerFileView) {
        return null;
    }
}
