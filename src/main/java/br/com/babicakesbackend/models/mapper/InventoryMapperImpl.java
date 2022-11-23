package br.com.babicakesbackend.models.mapper;

import br.com.babicakesbackend.models.dto.InventoryForm;
import br.com.babicakesbackend.models.dto.InventoryView;
import br.com.babicakesbackend.models.entity.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapperImpl implements MapStructMapper<Inventory, InventoryView, InventoryForm>{

    @Autowired
    private ProductMapperImpl productMapper;

    @Override
    public InventoryView entityToView(Inventory inventory) {
        return InventoryView.builder()
                .id(inventory.getId())
                .product(productMapper.entityToView(inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .build();
    }

    @Override
    public Inventory formToEntity(InventoryForm inventoryForm) {
        return Inventory.builder()
                .id(inventoryForm.getId())
                .product(productMapper.formToEntity(inventoryForm.getProduct()))
                .quantity(inventoryForm.getQuantity())
                .build();
    }

    @Override
    public InventoryForm viewToForm(InventoryView inventoryView) {
        return InventoryForm.builder()
                .id(inventoryView.getId())
                .product(productMapper.viewToForm(inventoryView.getProduct()))
                .quantity(inventoryView.getQuantity())
                .build();
    }

    @Override
    public InventoryForm entityToForm(Inventory inventory) {
        return InventoryForm.builder()
                .id(inventory.getId())
                .product(productMapper.entityToForm(inventory.getProduct()))
                .quantity(inventory.getQuantity())
                .build();
    }
}
