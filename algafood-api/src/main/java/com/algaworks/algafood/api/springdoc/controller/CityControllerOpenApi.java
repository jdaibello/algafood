package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.CityDTO;
import com.algaworks.algafood.api.dto.input.CityInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Cidades")
public interface CityControllerOpenApi {
    @Operation(summary = "Listar")
    CollectionModel<CityDTO> fetchAll();

    @Operation(summary = "Buscar por ID")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da cidade inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    CityDTO find(@Parameter(description = "ID da cidade", example = "1") Long cityId);

    @Operation(summary = "Adicionar")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Cidade cadastrada")})
    CityDTO add(CityInput cityInput);

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Cidade atualizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da cidade inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    CityDTO update(@Parameter(description = "ID da cidade", example = "1") Long cityId, CityInput city);

    @Operation(summary = "Excluir")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Cidade excluída"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID da cidade inválido",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cidade não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void delete(@Parameter(description = "ID da cidade", example = "1") Long cityId);
}
