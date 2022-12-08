package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface TemplatePixResource {

    @PostMapping
    ChargeView createImmediateCharge(@Valid @RequestBody ChargeForm charge);

    @GetMapping("/{txid}")
    ChargeView getImmediateCharge(@PathVariable("txid") String txid);
}
