package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.BannerFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerFileRepository extends JpaRepository<BannerFile, Long> {
}
