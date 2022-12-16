package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Formas de Pagamento dos Restaurantes")
public interface RestaurantPaymentMethodControllerOpenApi {

	@ApiOperation("Listar")
	List<PaymentMethodDTO> fetchAll(Long restaurantId);

	@ApiOperation("Anexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante e forma de pagamento anexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/forma de pagamento inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/forma de pagamento não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void attach(Long restaurantId, Long paymentMethodId);

	@ApiOperation("Desanexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante e forma de pagamentos desanexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/forma de pagamento inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/forma de pagamento não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void detach(Long restaurantId, Long paymentMethodId);

}