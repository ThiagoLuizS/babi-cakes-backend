package br.com.babicakesbackend.models.enumerators;

import br.com.babicakesbackend.models.dto.PropertyStringDTO;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BudgetStatusEnum {

    AWAITING_PAYMENT("Aguardando pagamento"),
    PAID_OUT("Pago"),
    PREPARING_ORDER("Preparando pedido"),
    WAITING_FOR_DELIVERY("Aguardando entregador"),
    ORDER_IS_OUT_FOR_DELIVERY("Pedido saiu para entrega"),
    ORDER_DELIVERED("Pedido entregue"),
    CANCELED_ORDER("Pedido cancelado");

    private String status;

    BudgetStatusEnum(String status) {
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
}
