package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Formas de Pagamento dos Restaurantes")
public interface RestaurantPaymentMethodControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<PaymentMethodDTO> fetchAll(Long restaurantId);

    @Operation(summary = "Anexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante e forma de pagamento anexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/forma de pagamento inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/forma de pagamento não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> attach(Long restaurantId, Long paymentMethodId);

    @Operation(summary = "Desanexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante e forma de pagamentos desanexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/forma de pagamento inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/forma de pagamento não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> detach(Long restaurantId, Long paymentMethodId);

}