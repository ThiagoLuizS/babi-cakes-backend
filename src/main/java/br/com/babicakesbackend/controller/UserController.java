package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.models.dto.UserFormGoogle;
import br.com.babicakesbackend.models.dto.UserSimpleForm;
import br.com.babicakesbackend.models.dto.UserView;
import br.com.babicakesbackend.resource.UserResource;
import br.com.babicakesbackend.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(value = "Usuários")
public class UserController implements UserResource {

    private final UserService service;

    @Override
    public UserView save(UserForm userForm) {
        return service.saveCustom(userForm);
    }

    @Override
    public UserView saveGoogle(UserFormGoogle userForm) {
        return service.saveGoogleCustom(userForm);
    }

    @Override
    public UserView update(UserSimpleForm userForm) {
        return service.updateCustom(userForm);
    }

    @Override
    public Page<UserView> getAllByPage(Pageable pageable) {
        return service.findByPage(pageable);
    }

    @Override
    public UserView getUserByEmail(String email) {
        return service.getUserViewByEmail(email);
    }
}
