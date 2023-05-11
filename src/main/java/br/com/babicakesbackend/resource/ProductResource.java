package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.ProductView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductResource {

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por criar um produto com imagem")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    ProductView save(@RequestParam(name = "form") String productFormJson,
              @RequestParam(name = "file") MultipartFile file) throws Exception;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/{id}", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por atualizar um produto com imagem")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    ProductView update(@PathVariable(name = "id") Long id,
                       @RequestParam(name = "form") String productFormJson,
                       @RequestParam(name = "file") MultipartFile file) throws Exception;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/inactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por excluir/inativar lógicamente um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void inactivateProduct(@PathVariable(name = "id") Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/reactivate/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por reativar um produto excluído lógicamente")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void reactivateProduct(@PathVariable(name = "id") Long id);

    @GetMapping("/pageable/category/{categoryId}")
    @ApiOperation(value = "Recurso responsavel por buscar os produtos por categoria com paginação")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<ProductView> getAllByPageAndCategory(Pageable pageable,
                                              @PathVariable(name = "categoryId") Long categoryId,
                                              @RequestParam("productName") String productName,
                                              @RequestParam(name = "excluded", required = false) List<Boolean> excluded);

    @GetMapping("/pageable/all")
    @ApiOperation(value = "Recurso responsavel por buscar todos os produtos com paginação")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<ProductView> getAllByPage(Pageable pageable,
                                   @RequestParam("productName") String productName,
                                   @RequestParam(name = "excluded", required = false) List<Boolean> excluded);
}
