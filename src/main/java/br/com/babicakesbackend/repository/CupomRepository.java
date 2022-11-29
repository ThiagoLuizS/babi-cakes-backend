package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Cupom;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.CupomStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

    Optional<Cupom> findByCode(String code);

    boolean existsByCode(String code);

    @Query("select c from Cupom c where c.user = :user " +
            "and (:status is null or c.cupomStatusEnum = :status)")
    Page<Cupom> findByUserAndCupomStatusEnum(@Param("user") User user, @Param("status") CupomStatusEnum status, Pageable pageable);

}
