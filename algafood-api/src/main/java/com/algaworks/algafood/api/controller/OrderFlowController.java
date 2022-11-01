package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.service.OrderFlowService;

import io.swagger.annotations.Api;

@Api(tags = "Fluxos dos Pedidos")
@RestController
@RequestMapping(value = "/orders/{orderCode}")
public class OrderFlowController {

	@Autowired
	private OrderFlowService orderFlowService;

	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable String orderCode) {
		orderFlowService.confirm(orderCode);
	}

	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String orderCode) {
		orderFlowService.cancel(orderCode);
	}

	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delivery(@PathVariable String orderCode) {
		orderFlowService.delivery(orderCode);
	}
}
