package br.com.babicakesbackend.models.enumerators;

import br.com.babicakesbackend.models.dto.PropertyStringDTO;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum CupomStatusEnum {

    ACTIVE("Ativo"),
    CANCELED("Cancelado"),
    EXPIRED("Expirado"),
    APPLIED("Aplicado"),
    FINISHED("Finalizado");

    private String status;

    CupomStatusEnum(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return ACTIVE.equals(this);
    }

    public boolean isCanceled() {
        return CANCELED.equals(this);
    }

    public boolean isExpired() {
        return EXPIRED.equals(this);
    }

    public boolean isApplied() {
        return APPLIED.equals(this);
    }

    public boolean isFinished() {
        return FINISHED.equals(this);
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

    public static CupomStatusEnum getInstance(String status) {
        return Arrays.stream(CupomStatusEnum.values())
                .filter(ft -> ft.status.equals(status))
                .findFirst().get();
    }
}
