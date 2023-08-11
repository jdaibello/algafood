package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.filter.OrderFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Pedidos")
public interface OrderControllerOpenApi {

    @Operation(summary = "Procurar com filtragem de cliente e restaurante")
    @Parameters({
            @Parameter(description = "Nomes das propriedades para filtrar na resposta, separados por vírgula")
    })
    PagedModel<OrderSummaryDTO> search(OrderFilter filter, Pageable pageable);

    @Operation(summary = "Buscar por código do pedido")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "Código UUID do pedido inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    OrderDTO find(String orderCode);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Pedido cadastrado")})
    OrderDTO add(OrderInput orderInput);

}