package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.resource.ParameterizationResource;
import br.com.babicakesbackend.service.ParameterizationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/parameterizations")
@RequiredArgsConstructor
@Api(value = "Parametrização")
public class ParameterizationController implements ParameterizationResource {

    private final ParameterizationService service;

    @Override
    public BigDecimal getFreightCost() {
        return service.findFreightCost();
    }
}
