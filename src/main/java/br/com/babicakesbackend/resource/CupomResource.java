package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.CupomForm;
import br.com.babicakesbackend.models.dto.CupomView;
import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface CupomResource {

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por criar um cupom")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void save(@Valid @RequestBody CupomForm cupomForm);

    @GetMapping
    @ApiOperation(value = "Recurso responsavel por buscar os cupons paginados")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<Cupom> getCupomByPage(Pageable pageable);

    @GetMapping("/all")
    @ApiOperation(value = "Recurso responsavel por buscar os cupons")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<CupomView> getCupomByUserAndCupomStatusEnum(@RequestHeader(name = "Authorization") String authorization,
                                                     @RequestParam(name = "status", required = false) CupomStatusEnum status,
                                                     Pageable pageable);

}
