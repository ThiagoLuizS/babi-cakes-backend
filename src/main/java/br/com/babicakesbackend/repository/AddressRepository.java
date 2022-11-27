package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Address;
import br.com.babicakesbackend.models.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserAndAddressMainIsTrue(User user);

    Page<Address> findByUser(User user, Pageable pageable);

    List<Address> findByUser(User user);
}
