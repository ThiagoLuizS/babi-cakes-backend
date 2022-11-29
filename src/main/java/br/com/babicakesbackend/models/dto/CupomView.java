package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CupomView {
    private Long id;
    private String code;
    private String description;
    private Date dateCreated;
    private Date dateExpired;
    private PropertyStringDTO cupomStatusEnum;
    private BigDecimal cupomValue;
    private boolean cupomIsValueMin;
    private BigDecimal cupomValueMin = BigDecimal.ZERO;
}
