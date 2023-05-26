package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("Select p From Product p " +
            "Inner Join p.category c " +
            "Where c.id = :categoryId " +
            "And (:productName is null or p.name like CONCAT('%', :productName, '%')) " +
            "And (coalesce(:excluded) is null or p.excluded in (:excluded))")
    Page<Product> findAllByCategoryIdAndNameStartsWithIgnoreCaseAndExcludedIn(
            @Param("categoryId") Long categoryId,
            @Param("productName") String productName,
            @Param("excluded") List<Boolean> excluded,
            Pageable pageable);

    @Query("Select p From Product p " +
            "Where 1 = 1 " +
            "And (:productName is null or p.name like CONCAT('%', :productName, '%')) " +
            "And (coalesce(:excluded) is null or p.excluded in (:excluded))")
    Page<Product> findAllByNameStartsWithIgnoreCaseAndExcludedIn(
            @Param("productName") String productName,
            @Param("excluded") List<Boolean> excluded,
            Pageable pageable);

    boolean existsByCode(Long code);
    Optional<Product> findByCode(Long code);

}
