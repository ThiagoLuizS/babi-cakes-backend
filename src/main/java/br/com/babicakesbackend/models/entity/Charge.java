package br.com.babicakesbackend.models.entity;

import br.com.babicakesbackend.models.enumerators.PixStatusEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_charge")
public class Charge {

    @Id
    @Column(name = "id_charge")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_budget")
    @ManyToOne(fetch = FetchType.EAGER)
    private Budget budget;

    @Column(name = "charge_expiration")
    private Integer expiration;

    @Column(name = "charge_expires_date")
    private Date expiresDate;

    @Column(name = "charge_created")
    private Date created;

    @Column(name = "charge_tax_ID")
    private String taxID;

    @Column(name = "charge_value")
    private BigDecimal value;

    @Column(name = "charge_correlation_ID")
    private String correlationID;

    @Column(name = "charge_transaction_ID")
    private String transactionID;

    @Enumerated(EnumType.STRING)
    @Column(name = "charge_status")
    private PixStatusEnum status;

    @Column(name = "charge_br_code")
    private String brCode;

}
