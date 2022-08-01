package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.OrderNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.OrderRepository;

@Service
public class OrderIssuanceService {

	@Autowired
	private OrderRepository orderRepository;

	public Order findOrFail(String orderCode) {
		return orderRepository.findByCode(orderCode).orElseThrow(() -> new OrderNotFoundException(orderCode));
	}
}
