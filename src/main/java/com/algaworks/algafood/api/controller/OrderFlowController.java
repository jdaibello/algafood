package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.OrderFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderId}")
public class OrderFlowController {

	@Autowired
	private OrderFlowService orderFlowService;

	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable Long orderId) {
		orderFlowService.confirm(orderId);
	}

	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable Long orderId) {
		orderFlowService.cancel(orderId);
	}

	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delivery(@PathVariable Long orderId) {
		orderFlowService.delivery(orderId);
	}
}
