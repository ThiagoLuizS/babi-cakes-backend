package br.com.babicakesbackend.service;

import br.com.babicakesbackend.exception.GlobalException;
import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.NotificationForm;
import br.com.babicakesbackend.models.entity.Device;
import br.com.babicakesbackend.models.entity.User;
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
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

    @Async
    public void sendNewEvent(EventForm eventForm) {
        sendMulticastData(Map.of(
                "titulo", eventForm.getTitle(),
                "descricao", eventForm.getDescription(),
                "image", eventForm.getImage()
        ));
    }
    @Async
    public void sendNotification(NotificationForm notificationForm) {
        sendMulticastNotification(notificationForm.getTitle(), notificationForm.getMessage(), "");
    }

    @Async
    public void sendNotificationByUser(NotificationForm notificationForm, Long userId) {
        sendMulticastNotificationByUser(notificationForm, "", userId);
    }

    private void sendMulticastData(Map<String, String> map) {
        List<Device> devices = deviceService.findEntityAll();
        List<String> tokens = devices.stream().map(Device::getToken).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(devices)) {
            try {
                MulticastMessage message = createData(map, tokens);
                BatchResponse batch = sendMulticast(message);
                if(batch.getFailureCount() > 0) {
                    removeUnregisteredTokens(batch, tokens);
                }
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

        getExtractTokensAndSendNotification(notificationForm.getTitle(), notificationForm.getMessage(), image, devices);
    }

    private void getExtractTokensAndSendNotification(String title, String messageParam, String image, List<Device> devices) {
        List<String> tokens = devices.stream().map(Device::getToken).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(devices)) {
            try {
                MulticastMessage message = createNotification(title, messageParam, image, tokens);
                BatchResponse batch = sendMulticast(message);
                if(batch.getFailureCount() > 0) {
                    removeUnregisteredTokens(batch, tokens);
                }
            } catch (Exception e) {
                log.error("<< sendMulticastData [error={}]", e.getMessage());
            }
        }
    }

    private void sendMulticastNotification(String title, String messageParam, String image) {
        List<Device> devices = deviceService.findEntityAll();
        getExtractTokensAndSendNotification(title, messageParam, image, devices);
    }

    private void removeUnregisteredTokens(BatchResponse batch, List<String> tokens) {
        AtomicInteger index = new AtomicInteger();
        tokens.stream().forEach((token) -> {
            SendResponse it = batch.getResponses().get(tokens.indexOf(index.get()));

            boolean unregisteredToken = it.getException().equals(MessagingErrorCode.UNREGISTERED);

            if(!it.isSuccessful() && unregisteredToken) {
                //TODO: Remove registro do repositorio
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
