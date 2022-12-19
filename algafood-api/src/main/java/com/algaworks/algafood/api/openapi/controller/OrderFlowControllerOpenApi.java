package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Fluxos dos Pedidos")
public interface OrderFlowControllerOpenApi {

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
	void confirm(String orderCode);

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
	void cancel(String orderCode);

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
	void delivery(String orderCode);

}