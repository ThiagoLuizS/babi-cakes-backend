package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.DeviceForm;
import br.com.babicakesbackend.models.dto.DeviceView;
import br.com.babicakesbackend.models.entity.Device;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.mapper.DeviceMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class DeviceService extends AbstractService<Device, DeviceView, DeviceForm> {

    private final DeviceRepository repository;
    private final DeviceMapperImpl deviceMapper;
    private final AuthenticationService authenticationService;

    public void saveCustom(String authorization, DeviceForm deviceForm) {
        try {
            User user = authenticationService.getUser(authorization);
            log.info(">> saveCustom [deviceBrand={}, deviceModel={}, userId={}]", deviceForm.getBrand(), deviceForm.getModel(), user.getId());

            if(repository.existsByUserAndBrandAndModel(user, deviceForm.getBrand(), deviceForm.getModel())) {
                Device device = repository.findByUserAndBrandAndModel(user, deviceForm.getBrand(), deviceForm.getModel());
                if(!StringUtils.equals(device.getToken(), deviceForm.getToken())) {
                    device.setToken(device.getToken());
                    device.setDeviceCreate(new Date());
                    repository.saveAndFlush(device);
                }
            } else {
                Device device = deviceMapper.formToEntity(deviceForm);
                device.setUser(user);
                device.setDeviceCreate(new Date());
                repository.saveAndFlush(device);
            }

        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
        }

    }

    public List<Device> findByUser(User user) {
        return repository.findByUser(user);
    }

    public List<Device> findEntityAll() {
        return repository.findAll();
    }

    @Override
    protected JpaRepository<Device, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Device, DeviceView, DeviceForm> getConverter() {
        return deviceMapper;
    }
}
