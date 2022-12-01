package br.com.babicakesbackend.resource;

import br.com.babicakesbackend.models.dto.EventForm;
import br.com.babicakesbackend.models.dto.EventView;
import br.com.babicakesbackend.models.dto.NotificationForm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Recurso responsavel por enviar dados por meio de eventos")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void sendNewEvent(@Valid @RequestBody EventForm eventForm);

    @PostMapping("/multicast-notification")
    @ResponseStatus(HttpStatus.CREATED)@ApiOperation(value = "Recurso responsavel por enviar notificação para todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })

    void sendNewNotification(@Valid @RequestBody NotificationForm notificationForm);

    @PostMapping("/multicast-notification/user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Recurso responsavel por enviar notificação para um usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void sendNewNotificationByUser(@Valid @RequestBody NotificationForm notificationForm, @PathVariable("userId") Long userId);

    @PostMapping("/connect")
    @ApiOperation(value = "Recurso responsavel por reestabelecer uma conexão com o FirebaseMessage")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void getConnect();

    @GetMapping("/user")
    @ApiOperation(value = "Recurso responsavel por criar buscar os usuários que não visualização as notificações")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    List<EventView> getByUserNotVisualized(@RequestHeader("Authorization") String authorization);

    @PutMapping("/vizualized/{eventId}")
    @ApiOperation(value = "Recurso responsavel por atualizar uma notificação depois de visualizada")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    void updateEventVizualized(@RequestHeader("Authorization") String authorization, @PathVariable("eventId") Long eventId);

    @GetMapping("/user/count/event-not-vizualized")
    @ApiOperation(value = "Recurso responsavel por buscar a quantidade de notificações não visualizadas")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Atributos do corpo da requisição podem está vazios"),
            @ApiResponse(code = 401, message = "Atributos de entreda/credenciais estão incorretos")
    })
    Integer countByDeviceUserAndVisualizedIsFalse(@RequestHeader("Authorization") String authorization);
}
