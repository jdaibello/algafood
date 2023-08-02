package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.dto.input.PaymentMethodInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.openapi.model.PaymentMethodsModelOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@Api(tags = "Formas de Pagamento")
public interface PaymentMethodControllerOpenApi {

    @ApiOperation(value = "Listar")
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "OK", response = PaymentMethodsModelOpenApi.class)
    })
    ResponseEntity<CollectionModel<PaymentMethodDTO>> fetchAll(ServletWebRequest request);

    @ApiOperation("Buscar por ID")
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

    @ApiOperation("Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")})
    PaymentMethodDTO add(PaymentMethodInput paymentMethodInput);

    @ApiOperation("Atualizar")
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

    @ApiOperation("Excluir")
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