package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.CupomForm;
import br.com.babicakesbackend.models.dto.CupomView;
import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
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
                .code(cupom.getCode())
                .description(cupom.getDescription())
                .dateCreated(cupom.getDateCreated())
                .dateExpired(cupom.getDateExpired())
                .cupomValue(cupom.getCupomValue())
                .cupomIsValueMin(cupom.isCupomIsValueMin())
                .cupomValueMin(cupom.getCupomValueMin())
                .cupomStatusEnum(cupom.getCupomStatusEnum().getProperty())
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
                .cupomValue(cupomForm.getCupomValue())
                .cupomIsValueMin(cupomForm.isCupomIsValueMin())
                .cupomValueMin(cupomForm.getCupomValueMin())
                .cupomStatusEnum(cupomForm.getCupomStatusEnum())
                .build();
    }

    @Override
    public CupomForm viewToForm(CupomView cupomView) {
        return CupomForm.builder()
                .id(cupomView.getId())
                .code(cupomView.getCode())
                .description(cupomView.getDescription())
                .dateCreated(cupomView.getDateCreated())
                .dateExpired(cupomView.getDateExpired())
                .cupomValue(cupomView.getCupomValue())
                .cupomIsValueMin(cupomView.isCupomIsValueMin())
                .cupomValueMin(cupomView.getCupomValueMin())
                .cupomStatusEnum(CupomStatusEnum.getInstance(cupomView.getCupomStatusEnum().getStatus()))
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
                .cupomValue(cupom.getCupomValue())
                .cupomIsValueMin(cupom.isCupomIsValueMin())
                .cupomValueMin(cupom.getCupomValueMin())
                .cupomStatusEnum(cupom.getCupomStatusEnum())
                .build();
    }
}
