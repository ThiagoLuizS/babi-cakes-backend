package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import br.com.babicakesbackend.models.dto.PropertyStringDTO;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.util.Date;
import java.util.List;

public interface BudgetResource {

    @PostMapping("/new-order")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por criar um novo orçamento")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    BudgetView createNewOrder(@RequestHeader(name = "Authorization") String authorization,
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
    @ApiOperation(value = "Recurso responsavel por buscar os orçamentos do usuário da sessão paginado")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<BudgetView> getBudgetPageByUser(@RequestHeader(name = "Authorization") String authorization,
                                     Pageable pageable);

    @GetMapping("/status")
    @ApiOperation(value = "Recurso responsavel por obter os status da esteira do orçamento/pedido")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    List<PropertyStringDTO> getBudgetStatus();

    @GetMapping("/pageable/all")
    @ApiOperation(value = "Recurso responsavel por obter buscar os orçamentos paginados")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<BudgetView> getBudgetPageAll(
            @RequestParam(name = "budgetCode", required = false) Long budgetCode,
            @RequestParam(name = "userName", required = false) Long userName,
            @RequestParam(name = "budgetStatus", required = false) BudgetStatusEnum budgetStatus,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(name = "finalDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date finalDate,
            Pageable pageable);

    @GetMapping("/id/{budgetId}")
    @ApiOperation(value = "Recurso responsavel por obter buscar os orçamentos paginados")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    BudgetView getBudgetByUserAndById(@RequestHeader(name = "Authorization") String authorization,
                                      @PathVariable(value = "budgetId") Long budgetId);

    @PutMapping("/paid/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por atualizar pedido para 'pago'")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void paidBudget(@PathVariable("budgetCode") Long budgetCode);

    @PutMapping("/preparing/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por atualizar pedido para 'preparando pedido'")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void preparingBudget(@PathVariable("budgetCode") Long budgetCode);

    @PutMapping("/waiting-for-delivery/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por atualizar pedido para 'aguardando entregador'")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void waitingForDelivery(@PathVariable("budgetCode") Long budgetCode);

    @PutMapping("/out-for-delivery/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por atualizar pedido para 'saiu para entregua'")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void budgetIsOutForDelivery(@PathVariable("budgetCode") Long budgetCode);

    @PutMapping("/delivery/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por atualizar pedido para 'entregue'")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void budgetDelivery(@PathVariable("budgetCode") Long budgetCode);
}
