package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Permissões dos Grupos")
public interface GroupPermissionControllerOpenApi {

    @ApiOperation("Listar")
    CollectionModel<PermissionDTO> fetchAll(Long groupId);

    @ApiOperation("Anexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Grupo e permissão anexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do grupo/permissão inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo/permissão não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> attach(Long groupId, Long permissionId);

    @ApiOperation("Desanexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Grupo e permissão desanexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do grupo/permissão inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Grupo/permissão não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> detach(Long groupId, Long permissionId);

}