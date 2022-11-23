package br.com.babicakesbackend.models.entity;

import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_cupom")
public class Cupom {

    @Id
    @Column(name = "id_cupom")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "cupom_code")
    private String code;

    @Column(name = "cupom_description")
    private String description;

    @Column(name = "cupom_date_created")
    private Date dateCreated;

    @Column(name = "cupom_date_expired")
    private Date dateExpired;

    @Enumerated(EnumType.STRING)
    @Column(name = "cupom_status")
    private CupomStatusEnum cupomStatusEnum;

    @Column(name = "cupom_percentage")
    private BigDecimal cupomPercentage;
}
