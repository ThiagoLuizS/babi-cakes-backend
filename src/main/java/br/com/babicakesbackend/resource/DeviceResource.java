package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.DeviceForm;
import br.com.babicakesbackend.models.dto.InventoryForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface DeviceResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por salvar dispositivo do usuário")
    void save(@RequestHeader(name = "Authorization") String authorization, @Valid @RequestBody DeviceForm deviceForm);

}
