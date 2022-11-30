package br.com.babicakesbackend.repository;

import br.com.babicakesbackend.models.entity.Event;
import br.com.babicakesbackend.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByDeviceUserAndVisualizedIsFalse(User user);

    Integer countByDeviceUserAndVisualizedIsFalse(User user);
}
