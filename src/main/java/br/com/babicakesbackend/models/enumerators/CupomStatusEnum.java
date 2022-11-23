package br.com.babicakesbackend.models.enumerators;

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
}
