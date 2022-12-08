package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.resource.TemplatePixResource;
import br.com.babicakesbackend.service.TemplatePixService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pixs")
@RequiredArgsConstructor
@Api(value = "Produtos")
public class TemplatePixController implements TemplatePixResource {

    private final TemplatePixService service;

    @Override
    public ChargeView createImmediateCharge(ChargeForm charge) {
        return service.createImmediateCharge(charge);
    }

    @Override
    public ChargeView getImmediateCharge(String txid) {
        return service.getImmediateCharge(txid);
    }
}
