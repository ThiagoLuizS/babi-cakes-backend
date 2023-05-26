package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c From Category c " +
            "Where 1 = 1 " +
            "and (coalesce(:show) is null or c.show in :show)")
    Page<Category> findAllByShowIn(@Param("show") List<Boolean> show, Pageable pageable);

    @Query("select c From Category c " +
            "Where 1 = 1 " +
            "and (coalesce(:show) is null or c.show in :show) " +
            "and (:categoryName is null or c.name like CONCAT('%', :categoryName, '%'))")
    Page<Category> findAllByNameStartsWithIgnoreCaseAndShowIn(@Param("categoryName") String categoryName, @Param("show") List<Boolean> show, Pageable pageable);


    List<Category> findAllByShow(Boolean show);

}
