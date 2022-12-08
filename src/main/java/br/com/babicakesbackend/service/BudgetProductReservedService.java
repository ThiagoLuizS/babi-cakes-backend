package br.com.babicakesbackend.service;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetProductReservedView;
import br.com.babicakesbackend.models.entity.Budget;
import br.com.babicakesbackend.models.entity.BudgetProductReserved;
import br.com.babicakesbackend.models.mapper.BudgetProductReservedMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.BudgetProductReservedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetProductReservedService
        extends AbstractService<BudgetProductReserved, BudgetProductReservedView, BudgetProductReservedForm>{

    private final BudgetProductReservedRepository repository;
    private final BudgetProductReservedMapperImpl budgetProductReservedMapper;

    public void saveAll(List<BudgetProductReserved> budgetProductReserves) {
        log.info(">> saveAll [length={}]", budgetProductReserves.size());
        repository.saveAll(budgetProductReserves);
        log.info("<< saveAll [length={}]", budgetProductReserves.size());
    }

    public List<BudgetProductReserved> findByBudget(Budget budget) {
        log.info(">> findByBudget [budget={}]", budget.getCode());
        List<BudgetProductReserved> list = repository.findByBudget(budget);
        log.info("<< findByBudget [length={}]", list.size());
        return list;
    }

    public List<BudgetProductReserved> findByBudgetCode(Long budgetId) {
        log.info(">> findByBudgetCode [budgetId={}]", budgetId);
        List<BudgetProductReserved> list = repository.findByBudgetId(budgetId);
        log.info("<< findByBudgetCode [length={}]", list.size());
        return list;
    }

    @Override
    protected JpaRepository<BudgetProductReserved, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<BudgetProductReserved, BudgetProductReservedView, BudgetProductReservedForm> getConverter() {
        return budgetProductReservedMapper;
    }
}
