package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.NotificationForm;
import br.com.babicakesbackend.models.entity.Device;
import br.com.babicakesbackend.models.entity.Event;
import br.com.babicakesbackend.models.entity.User;
import br.com.babicakesbackend.models.enumerators.EventOriginEnum;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.aspectj.util.FileUtil.listFiles;

@Slf4j
@Service
public class FirebaseService {

    private boolean firebaseConnectionState = false;

    private final String FIREBASE_DIRECTORY = "babi-cakes-firebase.json";
    private final String FIREBASE_ALREADY_EXISTS = "FirebaseApp name [DEFAULT] already exists";

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private EventService eventService;

    @Autowired
    private AuthenticationService authenticationService;

    public FirebaseService() {
        setupFirebase();
    }

    private void setupFirebase() {
        try {
            FileInputStream service = new FileInputStream(FIREBASE_DIRECTORY);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(service))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (NoSuchElementException e) {
            log.error("<< setupFirebase [error=Failure to read Firebase config file]");
        } catch (Exception e) {
            firebaseConnectionState = e.getMessage().contains(FIREBASE_ALREADY_EXISTS);
            if(firebaseConnectionState) {
                log.error("<< setupFirebase [error=Configuration already exists]");
            }
        }
    }

    public void sendNewEventByUser(String authorization, EventForm eventForm) {
        User user = authenticationService.getUser(authorization);
        sendNewEventByUser(eventForm, user);
    }

    @Async
    public void sendNewEventByUser(EventForm eventForm, User user) {
        sendMulticastData(Map.of(
                "titulo", eventForm.getTitle(),
                "descricao", eventForm.getMessage(),
                "image", eventForm.getImage()
        ), user);
    }

    @Async
    public void sendNewEvent(EventForm eventForm) {
        sendMulticastData(Map.of(
                "titulo", eventForm.getTitle(),
                "descricao", eventForm.getMessage(),
                "image", eventForm.getImage()
        ));
    }
    @Async
    public void sendNotification(NotificationForm notificationForm) {
        sendMulticastNotification(notificationForm, "");
    }

    @Async
    public void sendNotificationByUser(NotificationForm notificationForm, Long userId) {
        sendMulticastNotificationByUser(notificationForm, "", userId);
    }

    private void sendMulticastData(Map<String, String> map) {
        this.sendMulticastData(map, null);
    }

    private void sendMulticastData(Map<String, String> map, User user) {
        List<Device> devices = new ArrayList<>();
        if(Objects.isNull(user)) {
            devices = deviceService.findByUser(user);
        } else {
            devices = deviceService.findEntityAll();
        }

        List<String> tokens = devices.stream().map(Device::getToken).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(devices)) {
            try {
                MulticastMessage message = createData(map, tokens);
                BatchResponse batch = sendMulticast(message);

            } catch (Exception e) {
                log.error("<< sendMulticastData [error={}]", e.getMessage());
            }
        }
    }

    private void sendMulticastNotificationByUser(NotificationForm notificationForm, String image, Long userId) {
        Optional<User> user = userService.findEntityById(userId);

        if(!user.isPresent()) {
            throw new GlobalException("O usuário não existe");
        }

        List<Device> devices = deviceService.findByUser(user.get());

        if(CollectionUtils.isEmpty(devices)) {
            throw new GlobalException("O usuário não tem nenhum dispositivo cadastrado");
        }

        getExtractTokensAndSendNotification(notificationForm, image, devices);
    }

    private void getExtractTokensAndSendNotification(NotificationForm notificationForm, String image, List<Device> devices) {
        List<String> tokens = devices.stream().map(Device::getToken).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(devices)) {
            try {
                MulticastMessage message = createNotification(notificationForm.getTitle(), notificationForm.getMessage(), image, tokens);
                BatchResponse batch = sendMulticast(message);

                registredOcurrenceUnregisteredTokens(batch, tokens, devices, notificationForm);

            } catch (Exception e) {
                log.error("<< sendMulticastData [error={}]", e.getMessage());
            }
        }
    }

    private void sendMulticastNotification(NotificationForm notificationForm, String image) {
        List<Device> devices = deviceService.findEntityAll();
        getExtractTokensAndSendNotification(notificationForm, image, devices);
    }

    private void registredOcurrenceUnregisteredTokens(BatchResponse batch, List<String> tokens, List<Device> devices, NotificationForm notificationForm) {
        AtomicInteger index = new AtomicInteger(0);
        tokens.stream().forEach((token) -> {
            SendResponse it = batch.getResponses().get(tokens.indexOf(token));
            
            Device device = devices.stream().filter(ft -> ft.getToken().equals(token)).findFirst().get();

            Event event = Event.builder()
                    .device(device)
                    .title(notificationForm.getTitle())
                    .message(notificationForm.getMessage())
                    .image("")
                    .eventOriginEnum(EventOriginEnum.PUSH_NOTIFICATION)
                    .visualized(false)
                    .dateSend(new Date())
                    .build();
            
            if(!it.isSuccessful()) {
                event.setSend(false);
                eventService.saveCustom(event);
            } else {
                event.setSend(true);
                eventService.saveCustom(event);
            }

            
            index.getAndIncrement();
        });
    }

    private BatchResponse sendMulticast(MulticastMessage message) throws FirebaseMessagingException {
        return FirebaseMessaging.getInstance().sendMulticast(message);
    }

    private MulticastMessage createData(Map<String, String> map, List<String> tokens) {
        return MulticastMessage.builder()
                .putAllData(map)
                .addAllTokens(tokens)
                .build();
    }

    private MulticastMessage createNotification(String title, String body, String image, List<String> tokens) {
        return MulticastMessage.builder().setNotification(
                Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .setImage(image)
                        .build()
        ).addAllTokens(tokens).build();
    }

    public void connect() {
        setupFirebase();
    }
}
