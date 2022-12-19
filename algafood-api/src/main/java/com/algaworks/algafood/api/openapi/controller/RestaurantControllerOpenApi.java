package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.dto.RestaurantDTO;
import com.algaworks.algafood.api.dto.input.RestaurantInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestaurantControllerOpenApi {

	@ApiOperation("Listar")
	List<RestaurantDTO> fetchAll();

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	RestaurantDTO find(Long restaurantId);

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Restaurante cadastrado") })
	RestaurantDTO add(RestaurantInput restaurantInput);

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	RestaurantDTO update(Long restaurantId, RestaurantInput restaurantInput);

	@ApiOperation("Ativar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante ativado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void activate(Long restaurantId);

	@ApiOperation("Desativar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante desativado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void inactivate(Long restaurantId);

	@ApiOperation("Ativar vários")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurantes ativados"),
			@ApiResponse(
				responseCode = "400",
				description = "IDs dos restaurantes inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurantes não encontrados",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void activateMultiples(List<Long> restaurantIds);

	@ApiOperation("Desativar vários")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurantes desativados"),
			@ApiResponse(
				responseCode = "400",
				description = "IDs dos restaurantes inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurantes não encontrados",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void inactivateMultiples(List<Long> restaurantIds);

	@ApiOperation("Abrir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante aberto"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void open(Long restaurantId);

	@ApiOperation("Fechar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante fechado"),
			@ApiResponse(
				responseCode = "400",
				description = "IDs do restaurante inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void close(Long restaurantId);

}