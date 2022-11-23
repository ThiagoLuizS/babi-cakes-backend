package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByCategoryIdAndNameStartsWithIgnoreCase(Long categoryId, String productName, Pageable pageable);
    boolean existsByCode(Long code);
    Optional<Product> findByCode(Long code);

}
