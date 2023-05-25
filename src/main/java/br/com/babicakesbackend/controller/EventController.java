package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.EventView;
import br.com.babicakesbackend.models.dto.NotificationForm;
import br.com.babicakesbackend.resource.EventResource;
import br.com.babicakesbackend.service.EventService;
import br.com.babicakesbackend.service.FirebaseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Api(value = "Disparador de eventos/notificações")
public class EventController implements EventResource {

    private final FirebaseService service;

    private final EventService eventService;

    @Override
    public void sendNewEvent(EventForm eventForm) {
        service.sendNewEvent(eventForm);
    }

    @Override
    public void sendNewEventByUser(String authorization, EventForm eventForm) {
        service.sendNewEventByUser(authorization, eventForm);
    }

    @Override
    public void sendNewNotification(NotificationForm notificationForm) {
        eventService.sendNewNotificationAll(notificationForm);
    }

    @Override
    public void sendNewNotificationByUser(NotificationForm notificationForm, Long userId) {
        eventService.sendNewNotificationByUser(notificationForm, userId);
    }

    @Override
    public void getConnect() {
        service.connect();
    }

    @Override
    public List<EventView> getByUserNotVisualized(String authorization) {
        return eventService.findByUserNotVisualized(authorization);
    }

    @Override
    public void updateEventVizualized(String authorization, Long eventId) {
        eventService.updateEventVizualized(authorization, eventId);
    }

    @Override
    public Integer countByDeviceUserAndVisualizedIsFalse(String authorization) {
        return eventService.countByDeviceUserAndVisualizedIsFalse(authorization);
    }
}
