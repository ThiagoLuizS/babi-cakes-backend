package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.ParameterizationForm;
import br.com.babicakesbackend.models.dto.ParameterizationView;
import br.com.babicakesbackend.models.entity.Parameterization;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.ParameterizationMapperImpl;
import br.com.babicakesbackend.repository.ParameterizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterizationService extends AbstractService<Parameterization, ParameterizationView, ParameterizationForm>{

    private final ParameterizationRepository repository;
    private final ParameterizationMapperImpl parameterizationMapper;

    public BigDecimal findFreightCost() {
        log.info(">> findFreightCost");
        Optional<Parameterization> parameterization = repository.findAll().stream().findFirst();
        BigDecimal freightCost = BigDecimal.ZERO;
        if(parameterization.isPresent()) {
            freightCost = parameterization.get().getFreightCost();
            log.info("<< findFreightCost [freightCost={}]", freightCost);
            return freightCost;
        }
        log.info("<< findFreightCost [freightCost={}]", freightCost);
        return BigDecimal.ZERO;
    }


    @Override
    protected JpaRepository<Parameterization, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Parameterization, ParameterizationView, ParameterizationForm> getConverter() {
        return parameterizationMapper;
    }
}
