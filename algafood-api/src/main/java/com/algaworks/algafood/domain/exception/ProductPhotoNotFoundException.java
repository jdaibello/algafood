package com.algaworks.algafood.domain.exception;

public class ProductPhotoNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public ProductPhotoNotFoundException(String message) {
        super(message);
    }

    public ProductPhotoNotFoundException(Long restaurantId, Long productId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                productId, restaurantId));
    }
}
