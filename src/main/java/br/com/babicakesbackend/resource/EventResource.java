package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.NotificationForm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

public interface EventResource {

    @PostMapping("/multicast-event")
    @ResponseStatus(HttpStatus.CREATED)
    void sendNewEvent(@Valid @RequestBody EventForm eventForm);

    @PostMapping("/multicast-notification")
    @ResponseStatus(HttpStatus.CREATED)
    void sendNewNotification(@Valid @RequestBody NotificationForm notificationForm);

    @PostMapping("/multicast-notification/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    void sendNewNotificationByUser(@Valid @RequestBody NotificationForm notificationForm, @PathVariable("userId") Long userId);

    @PostMapping("/connect")
    void getConnect();
}
