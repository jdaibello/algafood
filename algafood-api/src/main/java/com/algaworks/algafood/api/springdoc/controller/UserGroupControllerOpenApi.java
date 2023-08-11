package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Grupos de Usuários")
public interface UserGroupControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<GroupDTO> fetchAll(Long userId);

    @Operation(summary = "Anexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Usuário e grupo anexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do usuário/grupo inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário/grupo não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> attach(Long userId, Long groupId);

    @Operation(summary = "Desanexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Usuário e grupo desanexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do usuário/grupo inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Usuário/grupo não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> detach(Long userId, Long groupId);

}