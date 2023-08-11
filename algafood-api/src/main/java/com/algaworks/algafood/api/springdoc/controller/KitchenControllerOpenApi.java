package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.api.dto.input.KitchenInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Tag(name = "Cozinhas")
public interface KitchenControllerOpenApi {

    @Operation(summary = "Listar com paginação")
    PagedModel<KitchenDTO> fetchAll(Pageable pageable);

    @Operation(summary = "Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da cozinha inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cozinha não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    KitchenDTO find(Long kitchenId);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cozinha cadastrada")})
    KitchenDTO add(KitchenInput kitchenInput);

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da cozinha inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    KitchenDTO update(Long kitchenId, KitchenInput kitchenInput);

    @Operation(summary = "Excluir")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Cozinha excluída"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da cozinha inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void delete(Long kitchenId);

}