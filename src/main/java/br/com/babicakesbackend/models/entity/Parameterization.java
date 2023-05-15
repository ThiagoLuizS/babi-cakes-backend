package br.com.babicakesbackend.models.entity;

import br.com.babicakesbackend.models.enumerators.ParameterizationEnvEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_parameterization")
public class Parameterization {

    @Id
    @Column(name = "id_param")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_freight_cost")
    private BigDecimal freightCost = BigDecimal.ZERO;

    @Column(name = "param_pix_api_key")
    private String apiKey;

    @Column(name = "param_pix_account_key")
    private String accountKey;

    @Column(name = "param_pix_client_key")
    private String clientKey;

    @Column(name = "param_paypal_client_key")
    private String paypalClientKey;

    @Column(name = "param_paypal_secret_key")
    private String paypalSecretKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "param_environment")
    private ParameterizationEnvEnum environment;

    @Column(name = "param_city_limit")
    private String cityLimit;

}
