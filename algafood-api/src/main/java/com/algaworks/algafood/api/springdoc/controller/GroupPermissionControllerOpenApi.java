package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Permissões dos Grupos")
public interface GroupPermissionControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<PermissionDTO> fetchAll(Long groupId);

    @Operation(summary = "Anexar")
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

    @Operation(summary = "Desanexar")
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