package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.openapi.controller.OrderFlowControllerOpenApi;
import com.algaworks.algafood.domain.service.OrderFlowService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderFlowController implements OrderFlowControllerOpenApi {

	@Autowired
	private OrderFlowService orderFlowService;

	@Override
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		orderFlowService.confirm(orderCode);
	}

	@Override
	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		orderFlowService.cancel(orderCode);
	}

	@Override
	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delivery(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		orderFlowService.delivery(orderCode);
	}
}
