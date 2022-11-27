package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.AddressForm;
import br.com.babicakesbackend.models.dto.AddressView;
import br.com.babicakesbackend.models.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapperImpl implements MapStructMapper<Address, AddressView, AddressForm>{
    @Override
    public AddressView entityToView(Address address) {
        return AddressView.builder()
                .id(address.getId())
                .cep(address.getCep())
                .addressMain(address.isAddressMain())
                .addressType(address.getAddressType())
                .addressName(address.getAddressName())
                .district(address.getDistrict())
                .lat(address.getLat())
                .lng(address.getLng())
                .state(address.getState())
                .city(address.getCity())
                .ddd(address.getDdd())
                .cityIbge(address.getCityIbge())
                .number(address.getNumber())
                .complement(address.getComplement())
                .build();
    }

    @Override
    public Address formToEntity(AddressForm addressForm) {
        return Address.builder()
                .id(addressForm.getId())
                .cep(addressForm.getCep())
                .addressType(addressForm.getAddressType())
                .addressName(addressForm.getAddressName())
                .district(addressForm.getDistrict())
                .lat(addressForm.getLat())
                .lng(addressForm.getLng())
                .state(addressForm.getState())
                .city(addressForm.getCity())
                .ddd(addressForm.getDdd())
                .cityIbge(addressForm.getCityIbge())
                .number(addressForm.getNumber())
                .complement(addressForm.getComplement())
                .build();
    }

    @Override
    public AddressForm viewToForm(AddressView addressView) {
        return AddressForm.builder()
                .id(addressView.getId())
                .cep(addressView.getCep())
                .addressType(addressView.getAddressType())
                .addressName(addressView.getAddressName())
                .district(addressView.getDistrict())
                .lat(addressView.getLat())
                .lng(addressView.getLng())
                .state(addressView.getState())
                .city(addressView.getCity())
                .ddd(addressView.getDdd())
                .cityIbge(addressView.getCityIbge())
                .build();
    }

    @Override
    public AddressForm entityToForm(Address address) {
        return AddressForm.builder()
                .id(address.getId())
                .cep(address.getCep())
                .addressType(address.getAddressType())
                .addressName(address.getAddressName())
                .district(address.getDistrict())
                .lat(address.getLat())
                .lng(address.getLng())
                .state(address.getState())
                .city(address.getCity())
                .ddd(address.getDdd())
                .cityIbge(address.getCityIbge())
                .build();
    }
}
