package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.BudgetProductReserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetProductReservedRepository extends JpaRepository<BudgetProductReserved, Long> {

    List<BudgetProductReserved> findByBudget(Budget budget);

    List<BudgetProductReserved> findByBudgetCode(Long budgetCode);
}
