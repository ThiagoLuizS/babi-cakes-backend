package br.com.babicakesbackend.resource;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

public interface ParameterizationResource {

    @GetMapping("/freight-cost")
    @ApiOperation(value = "Recurso responsavel por buscar o frete parametrizado")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    BigDecimal getFreightCost();
}
