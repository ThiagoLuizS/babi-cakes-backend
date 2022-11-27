package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.AddressForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemplateService {

    @Value("${value.url.cep}")
    private String urlCep;
    private final RestTemplate restTemplate;

    public AddressForm findAddressByCep(String cep) {
        try {
            return restTemplate.getForEntity(urlCep + "/" + cep, AddressForm.class).getBody();
        } catch (Exception e) {
            log.info("<< getAddressByCep [error={}]", e.getMessage());
            throw new GlobalException("Não foi possivel buscar o endereço pelo CEP informado");
        }
    }
}
