package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.ParameterizationForm;
import br.com.babicakesbackend.models.dto.ParameterizationView;
import br.com.babicakesbackend.models.entity.Parameterization;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.ParameterizationMapperImpl;
import br.com.babicakesbackend.repository.ParameterizationRepository;
import br.com.babicakesbackend.util.ConstantUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterizationService extends AbstractService<Parameterization, ParameterizationView, ParameterizationForm>{

    private final ParameterizationRepository repository;
    private final ParameterizationMapperImpl parameterizationMapper;

    public BigDecimal findFreightCost() {
        log.info(">> findFreightCost");
        Optional<Parameterization> parameterization = getParametrization();
        BigDecimal freightCost = BigDecimal.ZERO;
        if(parameterization.isPresent()) {
            freightCost = parameterization.get().getFreightCost();
            log.info("<< findFreightCost [freightCost={}]", freightCost);
            return freightCost;
        }
        log.info("<< findFreightCost [freightCost={}]", freightCost);
        return freightCost;
    }

    public boolean findOpenShop() {
        log.info(">> findOpenShop");
        Optional<Parameterization> parameterization = getParametrization();
        if(parameterization.isPresent()) {
            boolean openShop = parameterization.get().isOpenShop();
            log.info("<< findOpenShop [openShop={}]", openShop);
            return openShop;
        }
        log.info("<< findOpenShop [openShop={}]", false);
        return false;
    }

    public Optional<Parameterization> getParametrization() {

        List<Parameterization> params = repository.findAllByEnvironment(ConstantUtils.getEnvParameterization());

        if(CollectionUtils.isNotEmpty(params)) {
            return params.stream().findFirst();
        }

        throw new GlobalException("Nenhuma parametrização do sistema foi encontrada");
    }

    public ParameterizationView getParametrizationView() {

        List<Parameterization> params = repository.findAllByEnvironment(ConstantUtils.getEnvParameterization());

        if(CollectionUtils.isNotEmpty(params)) {
            Parameterization parameterization = params.stream().findFirst().get();
            return getConverter().entityToView(parameterization);
        }

        throw new GlobalException("Nenhuma parametrização do sistema foi encontrada");
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
