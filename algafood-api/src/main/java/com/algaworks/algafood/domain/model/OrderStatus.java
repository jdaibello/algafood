package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
	CREATED("Criado"),
	CONFIRMED("Confirmado", CREATED),
	DELIVERED("Entregue", CONFIRMED),
	CANCELED("Cancelado", CREATED);

	private final String description;
	private List<OrderStatus> previousStatus;

	OrderStatus(String description, OrderStatus... previousStatus) {
		this.description = description;
		this.previousStatus = Arrays.asList(previousStatus);
	}

	public String getDescription() {
		return this.description;
	}

	public boolean cantChangeTo(OrderStatus newStatus) {
		return !newStatus.previousStatus.contains(this);
	}
}
