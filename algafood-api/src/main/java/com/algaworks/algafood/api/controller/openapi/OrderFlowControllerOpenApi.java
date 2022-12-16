package com.algaworks.algafood.api.controller.openapi;

public interface OrderFlowControllerOpenApi {

	void confirm(String orderCode);

	void cancel(String orderCode);

	void delivery(String orderCode);

}