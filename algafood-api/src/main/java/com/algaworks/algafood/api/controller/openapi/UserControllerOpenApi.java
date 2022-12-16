package com.algaworks.algafood.api.controller.openapi;

import java.util.List;

import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.dto.input.PasswordInput;
import com.algaworks.algafood.api.dto.input.UserInput;
import com.algaworks.algafood.api.dto.input.UserWithPasswordInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UserControllerOpenApi {

	@ApiOperation("Listar")
	List<UserDTO> fetchAll();

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID do usuário inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Usuário não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	UserDTO find(Long userId);

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Usuário cadastrado") })
	UserDTO add(UserWithPasswordInput userInput);

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do usuário inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Usuário não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	UserDTO update(Long userId, UserInput userInput);

	@ApiOperation("Atualizar senha")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Senha atualizada"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do usuário inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Usuário não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void updatePassword(Long userId, PasswordInput passwordInput);

}