package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.InventoryForm;
import br.com.babicakesbackend.models.dto.InventoryView;
import br.com.babicakesbackend.models.dto.ProductView;
import br.com.babicakesbackend.models.entity.Inventory;
import br.com.babicakesbackend.models.entity.Product;
import br.com.babicakesbackend.models.mapper.InventoryMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.models.mapper.ProductMapperImpl;
import br.com.babicakesbackend.repository.InventoryRepository;
import br.com.babicakesbackend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService extends AbstractService<Inventory, InventoryView, InventoryForm> {

    private final InventoryRepository repository;
    private final InventoryMapperImpl inventoryMapper;
    private final ProductRepository productRepository;
    private final ProductMapperImpl productMapper;

    public InventoryView saveCustom(InventoryForm inventoryForm) {
        try {
            Optional<Product> product = productRepository.findById(inventoryForm.getProduct().getId());

            if(!product.isPresent()) {
                throw new GlobalException("O produto informado não existe");
            }

            Optional<Inventory> inventory = repository.findByProductId(inventoryForm.getProduct().getId());

            if(inventory.isPresent()) {
                inventory.get().setQuantity(inventoryForm.getQuantity());
                return getConverter().entityToView(repository.save(inventory.get()));
            } else {
                inventoryForm.setProduct(productMapper.entityToForm(product.get()));
                return save(inventoryForm);
            }
        } catch (Exception e) {
            log.error(">> saveCustom [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }

    }

    public InventoryView updateCustom(Long id, InventoryForm inventoryForm) {
        Optional<Inventory> inventory = repository.findById(id);

        if(!inventory.isPresent()) {
            throw new GlobalException("Estoque não encontrado");
        }

        inventoryForm.setId(id);

        return saveCustom(inventoryForm);
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

    public InventoryView findByViewProductId(Long productId) {
        log.info(">> findByProductId [productId={}]", productId);
        Optional<Inventory> inventory = repository.findByProductId(productId);

        if(!inventory.isPresent()) {
            throw new GlobalException("Nenhum estoque encontrado para o produto");
        }

        log.info("<< findByProductId [inventory isPresent={}]", inventory.isPresent());
        return getConverter().entityToView(inventory.get());
    }

    public Page<InventoryView> findAllPageByFilter(String productName, Pageable pageable) {
        log.info(">> findAllByCategoryId [productName={}, pageable={}]", productName, pageable);
        Page<Inventory> inventories = repository.findAllByProductNameStartsWithIgnoreCase(productName, pageable);
        log.info("<< findAllByCategoryId [inventoriesSize={}]", inventories.getContent().stream().count());
        List<InventoryView> view = inventories.getContent().stream().map(getConverter()::entityToView).collect(Collectors.toList());
        return new PageImpl<>(view);
    }

    public void deleteInvetory(Inventory inventories) {
        repository.delete(inventories);
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
