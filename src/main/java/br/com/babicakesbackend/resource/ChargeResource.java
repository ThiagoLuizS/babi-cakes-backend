package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.models.entity.Charge;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

public interface ChargeResource {

    @PostMapping("/payment-pix/{budgetId}")
    Charge createImmediateCharge(@RequestHeader("Authorization") String authorization, @PathVariable("budgetId") Long budgetId);

}
