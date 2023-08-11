package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.api.dto.input.StateInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Estados")
public interface StateControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<StateDTO> fetchAll();

    @Operation(summary = "Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do estado inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    StateDTO find(Long stateId);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Estado cadastrado")})
    StateDTO add(StateInput stateInput);

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do estado inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    StateDTO update(Long stateId, StateInput stateInput);

    @Operation(summary = "Excluir")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Estado excluído"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do estado inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Estado não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void delete(Long stateId);

}