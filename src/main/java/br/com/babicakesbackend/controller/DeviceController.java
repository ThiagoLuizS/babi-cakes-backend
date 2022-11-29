package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.DeviceForm;
import br.com.babicakesbackend.resource.DeviceResource;
import br.com.babicakesbackend.service.DeviceService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
@Api(value = "Dispositivos")
public class DeviceController implements DeviceResource {

    private final DeviceService service;

    @Override
    public void save(String authorization, DeviceForm deviceForm) {
        service.saveCustom(authorization, deviceForm);
    }
}
