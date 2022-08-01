package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.OrderDTOAssembler;
import com.algaworks.algafood.api.assembler.OrderInputDisassembler;
import com.algaworks.algafood.api.assembler.OrderSummaryDTOAssembler;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.service.OrderIssuanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

	@Autowired
	private OrderInputDisassembler orderInputDisassembler;

	@GetMapping
	public List<OrderSummaryDTO> fetchAll() {
		return orderSummaryDTOAssembler.toCollectionModel(orderRepository.findAll());
	}

	@GetMapping("/{orderCode}")
	public OrderDTO find(@PathVariable String orderCode) {
		Order order = service.findOrFail(orderCode);

		return orderDTOAssembler.toModel(order);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO add(@Valid @RequestBody OrderInput orderInput) {
		try {
			Order newOrder = orderInputDisassembler.toDomainObject(orderInput);

			// TODO get authenticated user data
			newOrder.setClient(new User());
			newOrder.getClient().setId(1L);

			newOrder = service.issue(newOrder);

			return orderDTOAssembler.toModel(newOrder);
		} catch (EntityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
