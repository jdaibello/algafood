package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.OrderRepository;

@Service
public class OrderFlowService {

	@Autowired
	private OrderIssuanceService orderIssuanceService;

	@Autowired
	private OrderRepository orderRepository;

	@Transactional
	public void confirm(String orderCode) {
		Order order = orderIssuanceService.findOrFail(orderCode);
		order.confirm();

		orderRepository.save(order);
	}

	@Transactional
	public void cancel(String orderCode) {
		Order order = orderIssuanceService.findOrFail(orderCode);
		order.cancel();

		orderRepository.save(order);
	}

	@Transactional
	public void delivery(String orderCode) {
		Order order = orderIssuanceService.findOrFail(orderCode);
		order.delivery();
	}
}
