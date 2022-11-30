package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.EventView;
import br.com.babicakesbackend.models.dto.NotificationForm;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/user")
    List<EventView> getByUserNotVisualized(@RequestHeader("Authorization") String authorization);

    @PutMapping("/vizualized/{eventId}")
    void updateEventVizualized(@RequestHeader("Authorization") String authorization, @PathVariable("eventId") Long eventId);

    @GetMapping("/user/count/event-not-vizualized")
    Integer countByDeviceUserAndVisualizedIsFalse(@RequestHeader("Authorization") String authorization);
}
