package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.UserForm;
import br.com.babicakesbackend.models.dto.UserView;
import br.com.babicakesbackend.models.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements MapStructMapper<User, UserView, UserForm> {

    @Override
    public UserView entityToView(User user) {
        return UserView.builder()
                .id(user.getId())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();
    }

    @Override
    public User formToEntity(UserForm userForm) {
        return User.builder()
                .id(userForm.getId())
                .name(userForm.getName())
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .birthday(userForm.getBirthday())
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
}
