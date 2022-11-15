package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.LoginForm;
import br.com.babicakesbackend.models.dto.TokenDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthenticationResource {

    @PostMapping
    @ApiOperation(value = "Recurso responsavel por obter o token")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    TokenDTO authentication(@Valid @RequestBody LoginForm loginForm) throws Exception;

}
