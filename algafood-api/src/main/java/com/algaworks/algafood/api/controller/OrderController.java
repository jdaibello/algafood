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
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.service.OrderIssuanceService;
import com.algaworks.algafood.infrastructure.repository.spec.OrderSpecs;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
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

	@ApiOperation("Procurar com filtragem de cliente e restaurante")
	@GetMapping
	public Page<OrderSummaryDTO> search(OrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {
		pageable = translatePageable(pageable);

		Page<Order> ordersPage = orderRepository.findAll(OrderSpecs.usingFilter(filter), pageable);
		List<OrderSummaryDTO> ordersSummaryDTO = orderSummaryDTOAssembler.toCollectionModel(ordersPage.getContent());

		return new PageImpl<>(ordersSummaryDTO, pageable, ordersPage.getTotalElements());
	}

	@ApiOperation("Buscar por código do pedido")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "Código UUID do pedido inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Pedido não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@GetMapping("/{orderCode}")
	public OrderDTO find(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		Order order = service.findOrFail(orderCode);

		return orderDTOAssembler.toModel(order);
	}

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Pedido cadastrado") })
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
