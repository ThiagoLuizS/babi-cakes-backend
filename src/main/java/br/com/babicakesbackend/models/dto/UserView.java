package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserView {
    private Long id;
    private String name;
    private String email;
    private Date birthday;
    private String phone;
    private UserStatusEnum status;
}
