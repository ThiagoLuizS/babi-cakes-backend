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
                .openShop(parameterization.isOpenShop())
                .build();
    }

    @Override
    public Parameterization formToEntity(ParameterizationForm parameterizationForm) {
        return Parameterization.builder()
                .id(parameterizationForm.getId())
                .freightCost(parameterizationForm.getFreightCost())
                .openShop(parameterizationForm.isOpenShop())
                .build();
    }

    @Override
    public ParameterizationForm viewToForm(ParameterizationView parameterizationView) {
        return ParameterizationForm.builder()
                .id(parameterizationView.getId())
                .freightCost(parameterizationView.getFreightCost())
                .openShop(parameterizationView.isOpenShop())
                .build();
    }

    @Override
    public ParameterizationForm entityToForm(Parameterization parameterization) {
        return ParameterizationForm.builder()
                .id(parameterization.getId())
                .freightCost(parameterization.getFreightCost())
                .openShop(parameterization.isOpenShop())
                .build();
    }

    @Override
    public Parameterization viewToEntity(ParameterizationView parameterizationView) {
        return null;
    }
}
