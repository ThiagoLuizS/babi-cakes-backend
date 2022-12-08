package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.models.entity.Charge;
import br.com.babicakesbackend.resource.ChargeResource;
import br.com.babicakesbackend.service.ChargeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charges")
@RequiredArgsConstructor
@Api(value = "Cobranças")
public class ChargeController implements ChargeResource {

    private final ChargeService chargeService;

    @Override
    public Charge createImmediateCharge(String authorization, Long budgetId) {
        return chargeService.createImmediateCharge(authorization, budgetId);
    }
}
