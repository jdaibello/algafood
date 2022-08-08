package com.algaworks.algafood.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
		super(message);
	}

	public ProductNotFoundException(Long restaurantId, Long productId) {
		this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d", productId,
				restaurantId));
	}
}
