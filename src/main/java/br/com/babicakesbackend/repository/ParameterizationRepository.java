package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Parameterization;
import br.com.babicakesbackend.models.enumerators.ParameterizationEnvEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterizationRepository extends JpaRepository<Parameterization, Long> {
    List<Parameterization> findAllByEnvironment(ParameterizationEnvEnum env);
}
