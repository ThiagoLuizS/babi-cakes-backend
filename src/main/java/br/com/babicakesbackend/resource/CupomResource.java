package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.CupomForm;
import br.com.babicakesbackend.models.entity.Cupom;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface CupomResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@Valid @RequestBody CupomForm cupomForm);

    @GetMapping
    Page<Cupom> getCupomByPage();

}
