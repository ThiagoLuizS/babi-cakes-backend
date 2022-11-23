package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.InventoryForm;
import br.com.babicakesbackend.models.dto.InventoryView;
import br.com.babicakesbackend.models.dto.ProductForm;
import br.com.babicakesbackend.models.entity.Inventory;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.mapper.InventoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.ProductFileMapperImpl;
import br.com.babicakesbackend.models.mapper.ProductMapperImpl;
import br.com.babicakesbackend.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService extends AbstractService<Inventory, InventoryView, InventoryForm> {

    private final InventoryRepository repository;
    private final InventoryMapperImpl inventoryMapper;
    private final ProductService productService;
    private final ProductMapperImpl productMapper;

    public void saveCustom(InventoryForm inventoryForm) {
        try {
            Optional<Product> product = productService.findEntityById(inventoryForm.getProduct().getId());

            if(!product.isPresent()) {
                throw new GlobalException("O produto informado não existe");
            }

            Optional<Inventory> inventory = repository.findByProductId(inventoryForm.getProduct().getId());

            if(inventory.isPresent()) {
                inventory.get().setQuantity(inventory.get().getQuantity() + inventoryForm.getQuantity());
                repository.save(inventory.get());
            } else {
                inventoryForm.setProduct(productMapper.entityToForm(product.get()));
                save(inventoryForm);
            }
        } catch (Exception e) {
            log.error(">> saveCustom [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }

    }

    public void saveByEntityCustom(Inventory inventory) {
        repository.save(inventory);
    }


    public Optional<Inventory> findByProductId(Long productId) {
        log.info(">> findByProductId [productId={}]", productId);
        Optional<Inventory> inventory = repository.findByProductId(productId);
        log.info("<< findByProductId [inventory isPresent={}]", inventory.isPresent());
        return inventory;
    }

    @Override
    protected JpaRepository<Inventory, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Inventory, InventoryView, InventoryForm> getConverter() {
        return inventoryMapper;
    }
}
