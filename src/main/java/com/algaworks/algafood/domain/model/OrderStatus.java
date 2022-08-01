package com.algaworks.algafood.domain.model;

public enum OrderStatus {
	CREATED("Criado"),
	CONFIRMED("Confirmado"),
	DELIVERED("Entegue"),
	CANCELED("Cancelado");

	private final String description;

	OrderStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
