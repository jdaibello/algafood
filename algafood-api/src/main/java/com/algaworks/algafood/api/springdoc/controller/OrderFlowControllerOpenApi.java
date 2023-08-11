package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Fluxos dos Pedidos")
public interface OrderFlowControllerOpenApi {

    @Operation(summary = "Confirmar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Pedido confirmado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Código UUID do pedido inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> confirm(String orderCode);

    @Operation(summary = "Cancelar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Pedido cancelado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Código UUID do pedido inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> cancel(String orderCode);

    @Operation(summary = "Entregar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Pedido entregue"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Código UUID do pedido inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> delivery(String orderCode);

}