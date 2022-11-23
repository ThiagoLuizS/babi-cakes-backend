package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findTop1ByOrderByCodeDesc();

    Optional<Budget> findByCode(Long code);

    Page<Budget> findByUser(User user, Pageable pageable);
}
