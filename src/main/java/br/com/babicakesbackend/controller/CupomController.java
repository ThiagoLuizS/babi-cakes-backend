package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.CupomForm;
import br.com.babicakesbackend.models.dto.CupomView;
import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
import br.com.babicakesbackend.resource.CupomResource;
import br.com.babicakesbackend.service.CupomService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cupons")
@RequiredArgsConstructor
@Api(value = "Cupons")
public class CupomController implements CupomResource {

    private final CupomService service;

    @Override
    public void save(CupomForm cupomForm) {
        service.saveCustom(cupomForm);
    }

    @Override
    public Page<Cupom> getCupomByPage(Pageable pageable) {
        return null;
    }

    @Override
    public Page<CupomView> getCupomByUserAndCupomStatusEnum(String authorization, CupomStatusEnum status, Pageable pageable) {
        return service.findByUserAndCupomStatusEnum(authorization, status, pageable);
    }
}
