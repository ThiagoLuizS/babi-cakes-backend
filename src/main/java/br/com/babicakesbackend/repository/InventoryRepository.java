package br.com.babicakesbackend.repository;


import br.com.babicakesbackend.models.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long id);


    Page<Inventory> findAllByProductNameStartsWithIgnoreCase(String productName, Pageable pageable);
}
