package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.CancelledOrderEvent;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.service.EmailSendingService;
import com.algaworks.algafood.domain.service.EmailSendingService.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class CancelledOrderClientNotificationListener {

    @Autowired
    private EmailSendingService emailSendingService;

    @TransactionalEventListener
    public void whenCancellingOrder(CancelledOrderEvent event) {
        Order order = event.getOrder();

        var message = Message.builder().subject(order.getRestaurant().getName() + " - Pedido cancelado")
                .body("cancelled-order.html").variable("order", order).recipient(order.getClient().getEmail()).build();

        emailSendingService.send(message);
    }
}
