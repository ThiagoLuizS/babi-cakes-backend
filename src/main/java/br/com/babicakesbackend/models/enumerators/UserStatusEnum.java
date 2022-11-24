package br.com.babicakesbackend.models.enumerators;

import java.util.List;

public enum UserStatusEnum {

    ACTIVE("Ativo"), BLOCKED("Bloqueado"), CANCELED("Cancelado"), ADMIN("Administrador");

    private String status;

    UserStatusEnum(String status) {
        this.status = status;
    }

    public List<UserStatusEnum> list() {
        return List.of(UserStatusEnum.values());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return ACTIVE.equals(this);
    }

    public boolean isAdmin() {
        return ADMIN.equals(this);
    }
}
