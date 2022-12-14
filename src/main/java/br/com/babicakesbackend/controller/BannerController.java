package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.BannerView;
import br.com.babicakesbackend.resource.BannerResource;
import br.com.babicakesbackend.service.BannerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
@Api(value = "Banners")
public class BannerController implements BannerResource {

    private final BannerService service;

    @Override
    public void save(String bannerFormJson, MultipartFile file) throws Exception {
        service.saveCustom(bannerFormJson, file);
    }

    @Override
    public List<BannerView> getAll() {
        return service.findAll();
    }
}
