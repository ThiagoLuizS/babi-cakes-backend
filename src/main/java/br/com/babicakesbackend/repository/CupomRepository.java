package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

    Optional<Cupom> findByCode(String code);

    boolean existsByCode(String code);

}
