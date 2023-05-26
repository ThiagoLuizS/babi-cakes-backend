package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.BudgetStatusEnum;
import br.com.babicakesbackend.service.BudgetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findTop1ByOrderByCodeDesc();

    Optional<Budget> findByCode(Long code);

    Page<Budget> findByUser(User user, Pageable pageable);

    Optional<Budget> findByUserAndId(User user, Long budgetId);

    Page<Budget> findByBudgetStatusEnum(BudgetStatusEnum status, Pageable pageable);


    @Query("select b From Budget b " +
            "Inner Join b.user u " +
            "Where 1 = 1 " +
            "And (:budgetCode is null or b.code = :budgetCode) " +
            "And (:userName is null or u.name like CONCAT('%', :userName, '%')) " +
            "And (:budgetStatus is null or b.budgetStatusEnum = :budgetStatus) " +
            "And b.dateCreateBudget BETWEEN :startDate and :finalDate ")
    Page<Budget> findByPageAll(
            @Param("budgetCode") Long budgetCode,
            @Param("userName") Long userName,
            @Param("budgetStatus") BudgetStatusEnum budgetStatus,
            @Param("startDate") Date startDate,
            @Param("finalDate") Date finalDate,
            Pageable pageable);

}
