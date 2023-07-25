package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.dto.input.GroupInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Grupos")
public interface GroupControllerOpenApi {

    @ApiOperation("Listar")
    List<GroupDTO> fetchAll();

    @ApiOperation("Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do grupo inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    GroupDTO find(Long groupId);

    @ApiOperation("Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Grupo cadastrado")})
    GroupDTO add(GroupInput groupInput);

    @ApiOperation("Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Grupo atualizado"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do grupo inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    GroupDTO update(Long groupId, GroupInput groupInput);

    @ApiOperation("Excluir")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Grupo excluído"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do grupo inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void delete(Long groupId);

}