package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.dto.input.PaymentMethodInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.springdoc.model.PaymentMethodsModelOpenApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Tag(name = "Formas de Pagamento")
public interface PaymentMethodControllerOpenApi {

    @Operation(summary = "Listar")
    @ApiResponse(responseCode = "200", description = "OK")
    ResponseEntity<CollectionModel<PaymentMethodDTO>> fetchAll(ServletWebRequest request);

    @Operation(summary = "Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da forma de pagamento inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Forma de pagamento não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<PaymentMethodDTO> find(Long paymentMethodId, ServletWebRequest request);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")})
    PaymentMethodDTO add(PaymentMethodInput paymentMethodInput);

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da forma de pagamento inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Forma de pagamento não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    PaymentMethodDTO update(Long paymentMethodId, PaymentMethodInput paymentMethodInput);

    @Operation(summary = "Excluir")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da forma de pagamento inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Forma de pagamento não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void delete(Long paymentMethodId);

}