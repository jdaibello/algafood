package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class OrderFlowService {

    @Autowired
    private OrderIssuanceService orderIssuanceService;

    @Transactional
    public void confirm(Long orderId) {
        Order order = orderIssuanceService.findOrFail(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new BusinessException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
                    order.getId(), order.getStatus().getDescription(), OrderStatus.CONFIRMED.getDescription()));
        }

        order.setStatus(OrderStatus.CONFIRMED);
        order.setConfirmationDate(OffsetDateTime.now());
    }
}
