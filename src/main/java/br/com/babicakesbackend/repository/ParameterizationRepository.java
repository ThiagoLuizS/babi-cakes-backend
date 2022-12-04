package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Parameterization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterizationRepository extends JpaRepository<Parameterization, Long> {
}
