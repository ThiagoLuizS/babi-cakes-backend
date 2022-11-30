package br.com.babicakesbackend.models.enumerators;

import br.com.babicakesbackend.models.dto.PropertyStringDTO;

import java.util.Arrays;

public enum EventOriginEnum {
    PUSH_NOTIFICATION("Notificação"), DATA_NOTIFICATION("Informações");

    private String status;

    EventOriginEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public PropertyStringDTO getProperty() {
        return PropertyStringDTO.builder()
                .type(this.name())
                .status(this.getStatus())
                .build();
    }

    public static EventOriginEnum getInstance(String status) {
        return Arrays.stream(EventOriginEnum.values())
                .filter(ft -> ft.status.equals(status))
                .findFirst().get();
    }
}
