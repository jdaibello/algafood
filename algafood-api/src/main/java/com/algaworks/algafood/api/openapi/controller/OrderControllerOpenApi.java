package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.filter.OrderFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface OrderControllerOpenApi {

	@ApiOperation("Procurar com filtragem de cliente e restaurante")
	Page<OrderSummaryDTO> search(OrderFilter filter, Pageable pageable);

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
	OrderDTO find(String orderCode);

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Pedido cadastrado") })
	OrderDTO add(OrderInput orderInput);

}