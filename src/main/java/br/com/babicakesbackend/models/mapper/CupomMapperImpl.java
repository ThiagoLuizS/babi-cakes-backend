package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.CupomForm;
import br.com.babicakesbackend.models.dto.CupomView;
import br.com.babicakesbackend.models.entity.Cupom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CupomMapperImpl implements MapStructMapper<Cupom, CupomView, CupomForm> {

    @Autowired
    private UserMapperImpl userMapper;

    @Override
    public CupomView entityToView(Cupom cupom) {
        return CupomView.builder()
                .id(cupom.getId())
                .user(userMapper.entityToView(cupom.getUser()))
                .code(cupom.getCode())
                .description(cupom.getDescription())
                .dateCreated(cupom.getDateCreated())
                .dateExpired(cupom.getDateExpired())
                .cupomPercentage(cupom.getCupomPercentage())
                .cupomStatusEnum(cupom.getCupomStatusEnum())
                .build();
    }

    @Override
    public Cupom formToEntity(CupomForm cupomForm) {
        return Cupom.builder()
                .id(cupomForm.getId())
                .user(userMapper.formToEntity(cupomForm.getUser()))
                .code(cupomForm.getCode())
                .description(cupomForm.getDescription())
                .dateCreated(cupomForm.getDateCreated())
                .dateExpired(cupomForm.getDateExpired())
                .cupomPercentage(cupomForm.getCupomPercentage())
                .cupomStatusEnum(cupomForm.getCupomStatusEnum())
                .build();
    }

    @Override
    public CupomForm viewToForm(CupomView cupomView) {
        return CupomForm.builder()
                .id(cupomView.getId())
                .user(userMapper.viewToForm(cupomView.getUser()))
                .code(cupomView.getCode())
                .description(cupomView.getDescription())
                .dateCreated(cupomView.getDateCreated())
                .dateExpired(cupomView.getDateExpired())
                .cupomPercentage(cupomView.getCupomPercentage())
                .cupomStatusEnum(cupomView.getCupomStatusEnum())
                .build();
    }

    @Override
    public CupomForm entityToForm(Cupom cupom) {
        return CupomForm.builder()
                .id(cupom.getId())
                .user(userMapper.entityToForm(cupom.getUser()))
                .code(cupom.getCode())
                .description(cupom.getDescription())
                .dateCreated(cupom.getDateCreated())
                .dateExpired(cupom.getDateExpired())
                .cupomPercentage(cupom.getCupomPercentage())
                .cupomStatusEnum(cupom.getCupomStatusEnum())
                .build();
    }
}
