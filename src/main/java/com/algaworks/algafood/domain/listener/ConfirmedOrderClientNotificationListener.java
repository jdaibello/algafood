package com.algaworks.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.algafood.domain.event.ConfirmedOrderEvent;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.EmailSendingService;
import com.algaworks.algafood.domain.service.EmailSendingService.Message;

@Component
public class ConfirmedOrderClientNotificationListener {

	@Autowired
	private EmailSendingService emailSendingService;

	@TransactionalEventListener
	public void whenConfirmingOrder(ConfirmedOrderEvent event) {
		Order order = event.getOrder();

		var message = Message.builder().subject(order.getRestaurant().getName() + " - Pedido confirmado")
				.body("confirmed-order.html").variable("order", order).recipient(order.getClient().getEmail()).build();

		emailSendingService.send(message);
	}
}
