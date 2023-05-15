package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.models.dto.UserView;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.UserOriginEnum;
import br.com.babicakesbackend.util.ConstantUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapperImpl implements MapStructMapper<User, UserView, UserForm> {

    @Override
    public UserView entityToView(User user) {
        return UserView.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .status(user.getStatus())
                .build();
    }

    @Override
    public User formToEntity(UserForm userForm) {
        return User.builder()
                .id(userForm.getId())
                .name(userForm.getName())
                .email(userForm.getEmail())
                .phone(userForm.getPhone())
                .birthday(userForm.getBirthday())
                .password(ConstantUtils.getSecurityHash(userForm.getPassword()))
                .status(userForm.getStatus())
                .origin(userForm.getOrigin())
                .build();
    }

    @Override
    public UserForm viewToForm(UserView userView) {
        return UserForm.builder()
                .id(userView.getId())
                .name(userView.getName())
                .email(userView.getEmail())
                .birthday(userView.getBirthday())
                .build();
    }

    @Override
    public UserForm entityToForm(User user) {
        return UserForm.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .birthday(user.getBirthday())
                .build();
    }

    @Override
    public User viewToEntity(UserView userView) {
        return null;
    }
}
