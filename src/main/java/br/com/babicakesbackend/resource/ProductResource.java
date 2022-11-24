package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.ProductView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

public interface ProductResource {

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    void save(@RequestParam(name = "form") String productFormJson,
              @RequestParam(name = "file") MultipartFile file) throws Exception;

    @GetMapping("/pageable/{categoryId}")
    Page<ProductView> getAllByPageAndCategory(Pageable pageable,
                                              @PathVariable(name = "categoryId") Long categoryId,
                                              @RequestParam("productName") String productName);
}
