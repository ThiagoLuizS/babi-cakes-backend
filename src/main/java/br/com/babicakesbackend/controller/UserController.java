package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.resource.UserResource;
import br.com.babicakesbackend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(value = "Usuários")
public class UserController implements UserResource {

    private final UserService service;

    @Override
    public void save(UserForm userForm) {
        service.saveCustom(userForm);
    }
}
