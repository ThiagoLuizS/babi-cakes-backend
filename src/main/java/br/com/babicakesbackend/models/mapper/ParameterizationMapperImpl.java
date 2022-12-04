package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.ParameterizationForm;
import br.com.babicakesbackend.models.dto.ParameterizationView;
import br.com.babicakesbackend.models.entity.Parameterization;
import org.springframework.stereotype.Component;

@Component
public class ParameterizationMapperImpl implements MapStructMapper<Parameterization, ParameterizationView, ParameterizationForm>{

    @Override
    public ParameterizationView entityToView(Parameterization parameterization) {
        return ParameterizationView.builder()
                .id(parameterization.getId())
                .freightCost(parameterization.getFreightCost())
                .build();
    }

    @Override
    public Parameterization formToEntity(ParameterizationForm parameterizationForm) {
        return null;
    }

    @Override
    public ParameterizationForm viewToForm(ParameterizationView parameterizationView) {
        return null;
    }

    @Override
    public ParameterizationForm entityToForm(Parameterization parameterization) {
        return null;
    }
}
