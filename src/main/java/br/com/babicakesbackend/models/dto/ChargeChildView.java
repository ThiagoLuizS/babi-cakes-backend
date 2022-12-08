package br.com.babicakesbackend.models.dto;

import br.com.babicakesbackend.models.enumerators.PixStatusEnum;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargeChildView {
    private CustomerChildView customer;
    private BigDecimal value;
    private String comment;
    private String identifier;
    private String correlationID;
    private String paymentLinkID;
    private String transactionID;
    @Enumerated(EnumType.STRING)
    private PixStatusEnum status;
    @JsonAlias("additionalInfo")
    private List<AdditionalInfo> additionalInfos;
    private Date expiresDate;
    private Date createdAt;
    private Date updatedAt;
    private Integer expiresIn;
    private String pixKey;
    private String brCode;
    private String paymentLinkUrl;
    private String qrCodeImage;
    private String globalID;
}
