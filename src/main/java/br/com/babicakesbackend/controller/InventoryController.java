package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.InventoryForm;
import br.com.babicakesbackend.models.dto.InventoryView;
import br.com.babicakesbackend.resource.InventoryResource;
import br.com.babicakesbackend.service.InventoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
@Api(value = "Inventário")
public class InventoryController implements InventoryResource {

    private final InventoryService service;

    @Override
    public InventoryView save(InventoryForm inventoryForm) {
        return service.saveCustom(inventoryForm);
    }

    @Override
    public InventoryView update(Long id, InventoryForm inventoryForm) {
        return service.updateCustom(id, inventoryForm);
    }

    @Override
    public InventoryView getByViewProductId(Long productId) {
        return service.findByViewProductId(productId);
    }

    @Override
    public Page<InventoryView> getAllPageByFilter(String productName, Pageable pageable) {
        return service.findAllPageByFilter(productName, pageable);
    }
}
