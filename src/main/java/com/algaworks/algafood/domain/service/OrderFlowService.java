package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.EmailSendingService.Message;

@Service
public class OrderFlowService {

	@Autowired
	private OrderIssuanceService orderIssuanceService;

	@Autowired
	private EmailSendingService emailSendingService;

	@Transactional
	public void confirm(String orderCode) {
		Order order = orderIssuanceService.findOrFail(orderCode);
		order.confirm();

		var message = Message.builder().subject(order.getRestaurant().getName() + " - Pedido confirmado")
				.body("O pedido de código <strong>" + order.getCode() + "</strong> foi confirmado!")
				.recipient(order.getClient().getEmail()).build();

		emailSendingService.send(message);
	}

	@Transactional
	public void cancel(String orderCode) {
		Order order = orderIssuanceService.findOrFail(orderCode);
		order.cancel();
	}

	@Transactional
	public void delivery(String orderCode) {
		Order order = orderIssuanceService.findOrFail(orderCode);
		order.delivery();
	}
}
