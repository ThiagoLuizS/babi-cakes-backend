package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.InventoryForm;
import br.com.babicakesbackend.resource.InventoryResource;
import br.com.babicakesbackend.service.InventoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
@Api(value = "Inventário")
public class InventoryController implements InventoryResource {

    private final InventoryService service;

    @Override
    public void save(InventoryForm inventoryForm) {
        service.saveCustom(inventoryForm);
    }
}
