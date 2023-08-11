package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.ProductDTO;
import com.algaworks.algafood.api.dto.input.ProductInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Produtos dos Restaurantes")
public interface RestaurantProductControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<ProductDTO> fetchAll(Long restaurantId, Boolean includeInactive);

    @Operation(summary = "Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/produto inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/produto não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ProductDTO find(Long restaurantId, Long productId);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Produto cadastrado")})
    ProductDTO add(Long restaurantId, ProductInput productInput);

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/produto inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/produto não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ProductDTO update(Long restaurantId, Long productId, ProductInput productInput);

}