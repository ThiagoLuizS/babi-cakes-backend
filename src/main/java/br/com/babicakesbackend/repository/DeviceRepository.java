package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Device;
import br.com.babicakesbackend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    boolean existsByUserAndBrandAndModel(User user, String brand, String model);

    Device findByUserAndBrandAndModel(User user, String brand, String model);

    boolean existsByUserAndToken(User user, String token);

    List<Device> findByUser(User user);

    List<Device> findByUserId(Long userId);


}
