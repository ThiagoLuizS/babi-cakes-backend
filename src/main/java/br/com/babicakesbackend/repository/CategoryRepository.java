package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByShowIn(List<Boolean> show, Pageable pageable);

    Page<Category> findAllByNameStartsWithIgnoreCaseAndShowIn(String categoryName, List<Boolean> show, Pageable pageable);

}
