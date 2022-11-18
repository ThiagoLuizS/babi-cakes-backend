package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.CategoryForm;
import br.com.babicakesbackend.models.entity.Category;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.net.URISyntaxException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save() throws Exception {
        URI uri = new URI("/api/categories");

        MockMultipartFile file = new MockMultipartFile("doces",
                "C:\\Users\\thiag\\Pictures\\flutter_images\\doces.jpg",
                "image/jpeg",
                "doces".getBytes());


        CategoryForm categoryForm = CategoryForm.builder()
                .name("Doces")
                .description("Doces")
                .file(file)
                .build();

        String json = new Gson().toJson(categoryForm);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .content(json)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
    }

}
