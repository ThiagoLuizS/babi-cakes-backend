package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.EventView;
import br.com.babicakesbackend.models.entity.Address;
import br.com.babicakesbackend.models.entity.Event;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.mapper.EventMapperImpl;
import br.com.babicakesbackend.models.mapper.MapStructMapper;
import br.com.babicakesbackend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class EventService extends AbstractService<Event, EventView, EventForm> {

    private final EventRepository repository;
    private final EventMapperImpl eventMapper;
    private final AuthenticationService authenticationService;

    public void saveCustom(Event event) {
        try {
            log.info(">> saveCustom [eventTitle={}, eventDeviceUserId={}]", event.getTitle(), event.getDevice().getUser().getId());
            repository.saveAndFlush(event);
        } catch (Exception e) {
            log.error("<< saveCustom [error={}]", e.getMessage());
        }
    }

    public List<EventView> findByUserNotVisualized(String authorization) {
        User user = authenticationService.getUser(authorization);
        List<Event> events = repository.findByDeviceUserAndVisualizedIsFalse(user);
        log.info("<< getByUserNotVisualized [eventsSize={}, userId={}]", events.size(), user.getId());
        return events.stream().map(eventMapper::entityToView).collect(Collectors.toList());
    }

    public void updateEventVizualized(String authorization, Long eventId) {
        log.info(">> updateEventVizualized [eventId={}]", eventId);
        try {
            authenticationService.getUser(authorization);

            Optional<Event> event = findEntityById(eventId);

            if(!event.isPresent()) {
                throw new GlobalException("Este evento não existe");
            }

            event.get().setVisualized(true);

            repository.save(event.get());
        } catch (Exception e) {
            log.error("<< updateEventVizualized [error={}]", e.getMessage());
            throw new GlobalException(e.getMessage());
        }

    }

    public Integer countByDeviceUserAndVisualizedIsFalse(String authorization) {
        User user = authenticationService.getUser(authorization);
        Integer countEvent = repository.countByDeviceUserAndVisualizedIsFalse(user);
        log.info("<< countByDeviceUserAndVisualizedIsFalse [countEventNoVizualized={}]", countEvent);
        return countEvent;
    }

    @Override
    protected JpaRepository<Event, Long> getRepository() {
        return repository;
    }

    @Override
    protected MapStructMapper<Event, EventView, EventForm> getConverter() {
        return eventMapper;
    }
}
