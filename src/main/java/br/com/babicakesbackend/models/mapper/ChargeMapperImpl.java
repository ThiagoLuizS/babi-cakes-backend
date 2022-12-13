package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.ChargeForm;
import br.com.babicakesbackend.models.dto.ChargeView;
import br.com.babicakesbackend.models.entity.Charge;
import org.springframework.stereotype.Component;

@Component
public class ChargeMapperImpl implements MapStructMapper<Charge, ChargeView, ChargeForm>{

    @Override
    public ChargeView entityToView(Charge charge) {
        return null;
    }

    @Override
    public Charge formToEntity(ChargeForm chargeForm) {
        return null;
    }

    @Override
    public ChargeForm viewToForm(ChargeView chargeView) {
        return null;
    }

    @Override
    public ChargeForm entityToForm(Charge charge) {
        return null;
    }

    @Override
    public Charge viewToEntity(ChargeView chargeView) {
        return Charge.builder()
                .expiration(chargeView.getCharge().getExpiresIn())
                .expiresDate(chargeView.getCharge().getExpiresDate())
                .created(chargeView.getCharge().getCreatedAt())
                .taxID(chargeView.getCharge().getCustomer().getTaxID().getTaxID())
                .value(chargeView.getCharge().getValue())
                .correlationID(chargeView.getCorrelationID())
                .transactionID(chargeView.getCharge().getTransactionID())
                .status(chargeView.getCharge().getStatus())
                .brCode(chargeView.getBrCode())
                .build();
    }
}
