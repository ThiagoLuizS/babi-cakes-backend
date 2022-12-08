package br.com.babicakesbackend.models.entity;

import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_budget")
public class Budget {

    @Id
    @Column(name = "id_budget")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_cupom")
    private Cupom cupom;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address address;

    @Column(name = "budget_code")
    private Long code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "budget_date_create")
    private Date dateCreateBudget;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "budget_date_finalized")
    private Date dateFinalizedBudget;

    @Enumerated(EnumType.STRING)
    @Column(name = "budget_status")
    private BudgetStatusEnum budgetStatusEnum;

    @Column(name = "budget_subtotal")
    private BigDecimal subTotal;

    @Column(name = "budget_amount")
    private BigDecimal amount;

    @Column(name = "budget_freight_cost")
    private BigDecimal freightCost;


//    @JoinTable(name = "tb_budget_charge",
//            joinColumns = {@JoinColumn(name = "budget_id")},
//            inverseJoinColumns = {@JoinColumn(name = "charge_id")})
    @OneToMany(mappedBy = "budget", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Charge> charges;
}
