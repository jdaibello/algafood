package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = "Responsáveis pelos Restaurantes")
public interface RestaurantResponsibleUserControllerOpenApi {

    @Operation(summary = "Listar")
    CollectionModel<UserDTO> fetchAll(@Parameter(description = "ID do restaurante", example = "1", required = true) Long restaurantId);

    @Operation(summary = "Anexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante e usuário anexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/usuário inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/usuário não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> attach(Long restaurantId, Long userId);

    @Operation(summary = "Desanexar")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Restaurante e usuário desanexados"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/usuário inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/usuário não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ResponseEntity<Void> detach(Long restaurantId, Long userId);

}