package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.api.dto.input.StateInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface StateControllerOpenApi {

	@ApiOperation("Listar")
	List<StateDTO> fetchAll();

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID do estado inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Estado não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	StateDTO find(Long stateId);

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Estado cadastrado") })
	StateDTO add(StateInput stateInput);

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Estado atualizado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do estado inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Estado não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	StateDTO update(Long stateId, StateInput stateInput);

	@ApiOperation("Excluir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Estado excluído"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do estado inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Estado não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void delete(Long stateId);

}