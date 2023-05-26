package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.dto.CategoryView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryResource {

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por criar uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    CategoryView save(@RequestParam(name = "form") String categoryFormJson,
              @RequestParam(name = "file") MultipartFile file) throws Exception;

    @GetMapping("/pageable/all")
    @ApiOperation(value = "Recurso responsavel por buscar as categorias paginadas filtrando por exclusão")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<CategoryView> getAllByPage(@RequestParam(name = "show", required = false) List<Boolean> show, Pageable pageable);

    @GetMapping("/fetch-product")
    @ApiOperation(value = "Recurso responsavel por buscar as categorias paginadas filtrando por exclusão")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    List<CategoryView> getAllCategoryAndFechProduct();

    @GetMapping("/pageable/filter")
    @ApiOperation(value = "Recurso responsavel por buscar as categorias paginadas filtrando por nome e exclusão")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Page<CategoryView> getAllFilterByPage(@RequestParam(name = "categoryName", required = false) String categoryName,
                                          @RequestParam(name = "show", required = false) List<Boolean> show, Pageable pageable);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/inactivate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Recurso responsavel por excluir/inativar lógicamente uma categori")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void inactivateCategory(@PathVariable(name = "id") Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path = "/reactivate/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por reativar uma categori excluído lógicamente")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void reactivateCategory(@PathVariable(name = "id") Long id);
}
