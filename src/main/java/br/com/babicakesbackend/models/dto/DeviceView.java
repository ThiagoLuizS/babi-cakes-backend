package br.com.babicakesbackend.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceView {
    private Long id;
    private UserView user;
    private String brand;
    private String model;
    private String token;
    private Date deviceCreate;
}
