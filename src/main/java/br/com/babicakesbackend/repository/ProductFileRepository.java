package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.ProductFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductFileRepository extends JpaRepository<ProductFile, Long> {
}
