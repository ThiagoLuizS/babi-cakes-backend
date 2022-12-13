package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Charge;
import br.com.babicakesbackend.models.enumerators.PixStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeRepository extends JpaRepository<Charge, Long> {

    List<Charge> findByBudgetId(Long budgetId);

    @Query("select c From Charge c inner join c.budget b " +
            "where c.status = :status")
    Page<Charge> findByStatus(@Param("status") PixStatusEnum status, Pageable pageable);
}
