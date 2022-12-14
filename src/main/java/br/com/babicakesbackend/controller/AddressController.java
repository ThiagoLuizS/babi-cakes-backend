package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.AddressForm;
import br.com.babicakesbackend.models.dto.AddressView;
import br.com.babicakesbackend.resource.AddressResource;
import br.com.babicakesbackend.service.AddressService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Api(value = "Endereços")
public class AddressController implements AddressResource {

    private final AddressService service;

    @Override
    public void save(String authorization, AddressForm addressForm) {
        service.saveCustom(authorization, addressForm);
    }

    @Override
    public AddressForm getAddressByCep(String cep) {
        return service.findAddressByCep(cep);
    }

    @Override
    public Page<AddressView> getPageByUser(String authorization, Pageable pageable) {
        return service.findPageByUser(authorization, pageable);
    }

    @Override
    public void updateAddressMain(String authorization, Long id) {
        service.updateAddressMain(authorization, id);
    }

    @Override
    public Optional<AddressView> getAddressByMain(String authorization) {
        return service.findByAddressMainIsTrueAndUser(authorization);
    }

    @Override
    public void deleteById(Long id) {
        service.delete(id);
    }
}
