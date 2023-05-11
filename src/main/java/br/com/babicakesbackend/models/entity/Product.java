package br.com.babicakesbackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_product")
public class Product {

    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category")
    private Category category;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_file")
    private ProductFile productFile;

    @Column(name = "product_code")
    private Long code;

    @Column(name = "product_name")
    private String name;

    @Column(name = "product_description")
    private String description;

    @Column(name = "product_value")
    private BigDecimal value;

    @Column(name = "product_discount_value")
    private BigDecimal discountValue;

    @Column(name = "product_percentage_value")
    private BigDecimal percentageValue;

    @Column(name = "product_minimun_order")
    private Integer minimumOrder;

    @Column(name = "product_exist_percentage")
    private boolean existPercentage;

    @Column(name = "product_tag")
    private String tag;

    @Column(name = "product_with_stock")
    private boolean withStock;

    @Column(name = "product_excluded")
    private boolean excluded;

}
