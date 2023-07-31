package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Responsáveis pelos Restaurantes")
public interface RestaurantResponsibleUserControllerOpenApi {

    @ApiOperation("Listar")
    CollectionModel<UserDTO> fetchAll(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Anexar")
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

    @ApiOperation("Desanexar")
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