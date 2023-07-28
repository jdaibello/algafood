package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    CREATED("Criado"),
    CONFIRMED("Confirmado", CREATED),
    DELIVERED("Entregue", CONFIRMED),
    CANCELLED("Cancelado", CREATED);

    private final String description;
    private final List<OrderStatus> previousStatus;

    OrderStatus(String description, OrderStatus... previousStatus) {
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean canChangeTo(OrderStatus newStatus) {
        return !cantChangeTo(newStatus);
    }

    public boolean cantChangeTo(OrderStatus newStatus) {
        return !newStatus.previousStatus.contains(this);
    }
}
