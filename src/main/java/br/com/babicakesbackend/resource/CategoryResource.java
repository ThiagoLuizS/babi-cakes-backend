package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.CategoryForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface CategoryResource {

    @PostMapping(value = "/upload")
    void save(@RequestBody CategoryForm categoryForm, @RequestParam(name = "file") MultipartFile file) throws Exception;
}
