package com.algaworks.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.service.OrderFlowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Fluxos dos Pedidos")
@RestController
@RequestMapping(value = "/orders/{orderCode}")
public class OrderFlowController {

	@Autowired
	private OrderFlowService orderFlowService;

	@ApiOperation("Confirmar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Pedido confirmado"),
			@ApiResponse(
				responseCode = "400",
				description = "Código UUID do pedido inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Pedido não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/confirmation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		orderFlowService.confirm(orderCode);
	}

	@ApiOperation("Cancelar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Pedido cancelado"),
			@ApiResponse(
				responseCode = "400",
				description = "Código UUID do pedido inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Pedido não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/cancellation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		orderFlowService.cancel(orderCode);
	}

	@ApiOperation("Entregar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Pedido entregue"),
			@ApiResponse(
				responseCode = "400",
				description = "Código UUID do pedido inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Pedido não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/delivery")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delivery(@ApiParam(
		value = "Código UUID do pedido",
		example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
		orderFlowService.delivery(orderCode);
	}
}
