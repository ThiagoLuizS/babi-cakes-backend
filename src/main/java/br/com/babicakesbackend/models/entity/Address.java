package br.com.babicakesbackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_address")
public class Address {

    @Id
    @Column(name = "id_address")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "adds_main")
    private boolean addressMain;

    @Column(name = "adds_cep")
    private Integer cep;

    @Column(name = "adds_type")
    private String addressType;

    @Column(name = "adds_name")
    private String addressName;

    @Column(name = "adds_state")
    private String state;

    @Column(name = "adds_district")
    private String district;

    @Column(name = "adds_lat")
    private String lat;

    @Column(name = "adds_lng")
    private String lng;

    @Column(name = "adds_city")
    private String city;

    @Column(name = "adds_city_ibge")
    private String cityIbge;

    @Column(name = "adds_dd")
    private String ddd;

    @Column(name = "adds_number")
    private String number;

    @Column(name = "adds_complement")
    private String complement;
}
