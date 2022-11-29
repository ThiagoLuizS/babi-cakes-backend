package br.com.babicakesbackend.controller;

import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.NotificationForm;
import br.com.babicakesbackend.resource.EventResource;
import br.com.babicakesbackend.service.FirebaseService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Api(value = "Disparador de eventos/notificações")
public class EventController implements EventResource {

    private final FirebaseService service;

    @Override
    public void sendNewEvent(EventForm eventForm) {
        service.sendNewEvent(eventForm);
    }

    @Override
    public void sendNewNotification(NotificationForm notificationForm) {
        service.sendNotification(notificationForm);
    }

    @Override
    public void sendNewNotificationByUser(NotificationForm notificationForm, Long userId) {
        service.sendNotificationByUser(notificationForm, userId);
    }

    @Override
    public void getConnect() {
        service.connect();
    }
}
