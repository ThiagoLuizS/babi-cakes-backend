package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.InventoryForm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface InventoryResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void save(@Valid @RequestBody InventoryForm inventoryForm);
}
