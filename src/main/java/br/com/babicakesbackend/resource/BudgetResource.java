package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface BudgetResource {

    @PostMapping("/new-order")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por criar um novo orçamento")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void createNewOrder(@RequestHeader(name = "Authorization") String authorization,
                        @RequestParam(name = "cupomCode", required = false) String cupomCode,
                        @RequestBody List<BudgetProductReservedForm> reservedForms);

    @PutMapping("/canceled/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por cancelar um orçamento")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void canceledBudget(@PathVariable("budgetCode") Long budgetCode);

    @GetMapping("/pageable")
    @ApiOperation(value = "Recurso responsavel por obter buscar os orçamentos paginados")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<BudgetView> getBudgetPageByUser(@RequestHeader(name = "Authorization") String authorization,
                                     Pageable pageable);
}
