package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.OrderDTOAssembler;
import com.algaworks.algafood.api.assembler.OrderSummaryDTOAssembler;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.service.OrderIssuanceService;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderIssuanceService service;

	@Autowired
	private OrderDTOAssembler orderDTOAssembler;

	@Autowired
	private OrderSummaryDTOAssembler orderSummaryDTOAssembler;

	@GetMapping
	public List<OrderSummaryDTO> fetchAll() {
		return orderSummaryDTOAssembler.toCollectionModel(orderRepository.findAll());
	}

	@GetMapping("/{orderId}")
	public OrderDTO find(@PathVariable Long orderId) {
		Order order = service.findOrFail(orderId);

		return orderDTOAssembler.toModel(order);
	}
}
