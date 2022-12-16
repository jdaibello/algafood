package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.OrderDTOAssembler;
import com.algaworks.algafood.api.assembler.OrderInputDisassembler;
import com.algaworks.algafood.api.assembler.OrderSummaryDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.OrderControllerOpenApi;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.service.OrderIssuanceService;
import com.algaworks.algafood.infrastructure.repository.spec.OrderSpecs;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/orders")
public class OrderController implements OrderControllerOpenApi {

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

	@Override
	@GetMapping
	public Page<OrderSummaryDTO> search(OrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {
		pageable = translatePageable(pageable);

		Page<Order> ordersPage = orderRepository.findAll(OrderSpecs.usingFilter(filter), pageable);
		List<OrderSummaryDTO> ordersSummaryDTO = orderSummaryDTOAssembler.toCollectionModel(ordersPage.getContent());

		return new PageImpl<>(ordersSummaryDTO, pageable, ordersPage.getTotalElements());
	}

	@Override
	@GetMapping("/{orderCode}")
	public OrderDTO find(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		Order order = service.findOrFail(orderCode);

		return orderDTOAssembler.toModel(order);
	}

	@Override
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

	private Pageable translatePageable(Pageable apiPageable) {
		var mapper = Map.of("code", "code", "subtotal", "subtotal", "shippingFee", "shippingFee", "creationDate",
				"creationdate", "restaurant.id", "restaurant.id", "restaurant.name", "restaurant.name", "client.id",
				"client.id", "client.name", "client.name");

		return PageableTranslator.translate(apiPageable, mapper);
	}
}
