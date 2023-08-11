package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.dto.input.PasswordInput;
import com.algaworks.algafood.api.dto.input.UserInput;
import com.algaworks.algafood.api.dto.input.UserWithPasswordInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Usuários")
public interface UserControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<UserDTO> fetchAll();

    @Operation(summary = "Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do usuário inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    UserDTO find(Long userId);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Usuário cadastrado")})
    UserDTO add(UserWithPasswordInput userInput);

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Usuário atualizado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do usuário inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    UserDTO update(Long userId, UserInput userInput);

    @Operation(summary = "Atualizar senha")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Senha atualizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do usuário inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void updatePassword(Long userId, PasswordInput passwordInput);

}