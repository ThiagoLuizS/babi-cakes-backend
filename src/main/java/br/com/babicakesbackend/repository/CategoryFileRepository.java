package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.CategoryFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryFileRepository extends JpaRepository<CategoryFile, Long> {
}
