package com.algaworks.algafood.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(Long orderId) {
		this(String.format("Não existe um pedido com código %d", orderId));
	}
}
