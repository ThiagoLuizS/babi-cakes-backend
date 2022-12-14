package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.AddressForm;
import br.com.babicakesbackend.models.entity.Parameterization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateAddressService {

    @Value("${value.url.cep}")
    private String urlCep;
    private final RestTemplate restTemplate;
    private final ParameterizationService parameterizationService;

    public AddressForm findAddressByCep(String cep) {
        try {
            Optional<Parameterization> parameterization = parameterizationService.getParametrization();
            AddressForm form = restTemplate.getForEntity(urlCep + "/" + cep, AddressForm.class).getBody();
            if(!StringUtils.containsIgnoreCase(parameterization.get().getCityLimit(), form.getCity())) {
                throw new GlobalException("O endereço está fora dos limites de entrega da loja");
            }
            return form;
        } catch (Exception e) {
            log.info("<< getAddressByCep [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }
    }
}
