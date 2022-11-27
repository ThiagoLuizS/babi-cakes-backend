package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.AddressForm;
import br.com.babicakesbackend.models.dto.AddressView;
import br.com.babicakesbackend.models.entity.Address;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.mapper.AddressMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressService extends AbstractService<Address, AddressView, AddressForm> {

    private final AddressRepository repository;
    private final AddressMapperImpl addressMapper;
    private final AuthenticationService authenticationService;
    private final TemplateService templateService;

    public void saveCustom(String authorization, AddressForm addressForm) {
        try {
            log.info(">> saveCustom [form={}]", addressForm);
            User user = authenticationService.getUser(authorization);
            updateAddressListMain(user);
            Address address = getConverter().formToEntity(addressForm);
            address.setUser(user);
            address.setAddressMain(true);
            repository.save(address);
            log.info("<< saveCustom [save Id={}]", address.getId());
        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }

    public Optional<Address> findByUserAndAddressMainIsTrue(User user) {
        return repository.findByUserAndAddressMainIsTrue(user);
    }

    public AddressForm findAddressByCep(String cep) {
        return templateService.findAddressByCep(cep);
    }

    public void updateAddressMain(String authorization, Long id) {
        log.info(">> updateAddressMain [id={}]", id);
        try {
            User user = authenticationService.getUser(authorization);
            Optional<Address> address =  findEntityById(id);
            if(!address.isPresent()) {
                throw new GlobalException("Endereço não existe");
            }

            updateAddressListMain(user);

            address.get().setAddressMain(true);

            repository.save(address.get());
        } catch (Exception e) {
            log.error("<< updateAddressMain [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }

    }

    private void updateAddressListMain(User user) {
        List<Address> addressList = repository.findByUser(user);

        addressList.stream().forEach(add -> {
            if(add.isAddressMain()) {
                add.setAddressMain(false);
            }
        });

        repository.saveAll(addressList);
    }

    public Page<AddressView> findPageByUser(String authorization, Pageable pageable) {
        User user = authenticationService.getUser(authorization);
        log.info(">> findPageByUser [pageable={}]", pageable);
        Page<Address> page = repository.findByUser(user, pageable);
        log.info("<< findPageByUser [page size={}]", pageable.getPageSize());
        List<AddressView> views = page.getContent().stream().map(addressMapper::entityToView).collect(Collectors.toList());
        return new PageImpl<>(views);
    }

    @Override
    protected JpaRepository<Address, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Address, AddressView, AddressForm> getConverter() {
        return addressMapper;
    }
}
