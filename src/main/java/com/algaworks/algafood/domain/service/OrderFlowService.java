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
    public void confirm(String orderCode) {
        Order order = orderIssuanceService.findOrFail(orderCode);
        order.confirm();
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
