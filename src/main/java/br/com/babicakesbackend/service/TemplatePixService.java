package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.models.entity.Parameterization;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = GlobalException.class)
public class TemplatePixService extends HeaderTemplateService {

    private final RestTemplate restTemplate;
    private final ParameterizationService parameterizationService;

    @Value("${value.url.pix}")
    private String urlPath;


    public ChargeView createImmediateCharge(ChargeForm charge) {
        try {
            log.info(">> createPayment [correlationID={}]", charge.getCorrelationID());
            Optional<Parameterization> param = parameterizationService.getParametrization();
            HttpEntity<ChargeForm> request = new HttpEntity<>(charge, createHeaderPixForBasicAuthorization(param.get().getApiKey()));
            ResponseEntity<ChargeView> response = restTemplate.postForEntity(urlPath + "charge", request, ChargeView.class);
            if(Objects.equals(response.getStatusCode(), HttpStatus.CREATED) || Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
                return response.getBody();
            }
            throw new GlobalException("Pagamento com PIX não efetuado statusCode=" + response.getStatusCodeValue());
        } catch (Exception e) {
            log.error("<< createPayment [error={}]", e.getMessage());
            throw new GlobalException("Não foi possivel emitir o pagamento com o PIX");
        }
    }

    public ChargeView getImmediateCharge(String transactionID) {
        try {
            log.info(">> getImmediateCharge [transactionID={}]", transactionID);
            Optional<Parameterization> param = parameterizationService.getParametrization();
            HttpEntity<ChargeForm> request = new HttpEntity<>(createHeaderPixForBasicAuthorization(param.get().getApiKey()));
            ResponseEntity<ChargeView> response = restTemplate.exchange(urlPath + "charge/" + transactionID, HttpMethod.GET, request, ChargeView.class);
            if(Objects.equals(response.getStatusCode(), HttpStatus.CREATED)) {
                return response.getBody();
            }
            throw new GlobalException("Pagamento com PIX não efetuado statusCode=" + response.getStatusCodeValue());
        } catch (Exception e) {
            log.error("<< getImmediateCharge [error={}]", e.getMessage());
            throw new GlobalException("Não foi possivel emitir o pagamento com o PIX");
        }
    }

    public ChargeView getImmediateChargeNoAction(String transactionID) {
        try {
            log.info(">> getImmediateChargeNoAction [transactionID={}]", transactionID);
            Optional<Parameterization> param = parameterizationService.getParametrization();
            HttpEntity<ChargeForm> request = new HttpEntity<>(createHeaderPixForBasicAuthorization(param.get().getApiKey()));
            ResponseEntity<ChargeView> response = restTemplate.exchange(urlPath + "charge/" + transactionID, HttpMethod.GET, request, ChargeView.class);
            if(Objects.equals(response.getStatusCode(), HttpStatus.CREATED)
                    || Objects.equals(response.getStatusCode(), HttpStatus.OK)) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("<< getImmediateCharge [error={}]", e.getMessage());
        }

        return null;
    }


}
