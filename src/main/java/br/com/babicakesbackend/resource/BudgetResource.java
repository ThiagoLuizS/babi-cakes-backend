package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.BudgetProductReservedForm;
import br.com.babicakesbackend.models.dto.BudgetView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface BudgetResource {

    @PostMapping("/new-order")
    @ResponseStatus(HttpStatus.CREATED)
    void createNewOrder(@RequestHeader(name = "Authorization") String authorization,
                        @RequestParam(name = "cupomCode", required = false) String cupomCode,
                        @RequestBody List<BudgetProductReservedForm> reservedForms);

    @PutMapping("/canceled/{budgetCode}")
    @ResponseStatus(HttpStatus.OK)
    void canceledBudget(@PathVariable("budgetCode") Long budgetCode);

    @GetMapping("/pageable")
    Page<BudgetView> getBudgetPageByUser(@RequestHeader(name = "Authorization") String authorization,
                                     Pageable pageable);
}
