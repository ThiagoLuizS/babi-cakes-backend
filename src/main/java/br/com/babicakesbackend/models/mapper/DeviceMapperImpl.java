package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.DeviceForm;
import br.com.babicakesbackend.models.dto.DeviceView;
import br.com.babicakesbackend.models.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapperImpl implements MapStructMapper<Device, DeviceView, DeviceForm> {
    @Override
    public DeviceView entityToView(Device device) {
        return null;
    }

    @Override
    public Device formToEntity(DeviceForm deviceForm) {
        return Device.builder()
                .id(deviceForm.getId())
                .brand(deviceForm.getBrand())
                .model(deviceForm.getModel())
                .token(deviceForm.getToken())
                .build();
    }

    @Override
    public DeviceForm viewToForm(DeviceView deviceView) {
        return null;
    }

    @Override
    public DeviceForm entityToForm(Device device) {
        return null;
    }

    @Override
    public Device viewToEntity(DeviceView deviceView) {
        return null;
    }
}
