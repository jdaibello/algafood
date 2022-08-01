package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderFlowService {

    @Autowired
    private OrderIssuanceService orderIssuanceService;

    @Transactional
    public void confirm(Long orderId) {
        Order order = orderIssuanceService.findOrFail(orderId);
        order.confirm();
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderIssuanceService.findOrFail(orderId);
        order.cancel();
    }

    @Transactional
    public void delivery(Long orderId) {
        Order order = orderIssuanceService.findOrFail(orderId);
        order.delivery();
    }
}
