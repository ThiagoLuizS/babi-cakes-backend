package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.AddressForm;
import br.com.babicakesbackend.models.dto.AddressView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface AddressResource {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por criar um novo endereço")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void save(@RequestHeader(name = "Authorization") String authorization, @Valid @RequestBody AddressForm addressForm);

    @GetMapping("/cep/{cep}")
    @ApiOperation(value = "Recurso responsavel por buscar endereço por cep")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    AddressForm getAddressByCep(@PathVariable("cep") String cep);

    @GetMapping("/all")
    @ApiOperation(value = "Recurso responsavel por buscar os endereços do usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<AddressView> getPageByUser(@RequestHeader(name = "Authorization") String authorization, Pageable pageable);

    @PutMapping("/main/{id}")
    @ApiOperation(value = "Recurso responsavel por criar atualizar o endereço para o endereço principal")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void updateAddressMain(@RequestHeader(name = "Authorization") String authorization, @PathVariable("id") Long id);
}
