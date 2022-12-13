package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.entity.Charge;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface ChargeResource {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/payment-pix/{budgetId}")
    Charge createImmediateCharge(@RequestHeader("Authorization") String authorization, @PathVariable("budgetId") Long budgetId);

}
