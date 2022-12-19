package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.dto.CityDTO;
import com.algaworks.algafood.api.dto.input.CityInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CityControllerOpenApi {
	@ApiOperation("Listar")
	public List<CityDTO> fetchAll();

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID da cidade inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Cidade não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	public CityDTO find(@ApiParam(value = "ID da cidade", example = "1") Long cityId);

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Cidade cadastrada") })
	public CityDTO add(CityInput cityInput);

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Cidade atualizada"),
			@ApiResponse(
				responseCode = "400",
				description = "ID da cidade inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Cidade não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	public CityDTO update(@ApiParam(value = "ID da cidade", example = "1") Long cityId, CityInput city);

	@ApiOperation("Excluir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Cidade excluída"),
			@ApiResponse(
				responseCode = "400",
				description = "ID da cidade inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Cidade não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	public void delete(@ApiParam(value = "ID da cidade", example = "1") Long cityId);
}
