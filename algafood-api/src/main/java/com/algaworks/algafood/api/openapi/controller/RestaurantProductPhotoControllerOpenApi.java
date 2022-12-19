package com.algaworks.algafood.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import com.algaworks.algafood.api.dto.ProductPhotoDTO;
import com.algaworks.algafood.api.dto.input.ProductPhotoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Fotos dos Produtos dos Restaurantes")
public interface RestaurantProductPhotoControllerOpenApi {

	@ApiOperation(
		value = "Buscar por ID do restaurante e do produto",
		produces = "application/json, image/jpeg, image/png")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/produto inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/produto não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	ProductPhotoDTO find(Long restaurantId, Long productId);

	@ApiOperation(value = "Buscar por ID do restaurante e do produto", hidden = true)
	ResponseEntity<?> servePhoto(Long restaurantId, Long productId, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/produto inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/produto não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	ProductPhotoDTO updatePhoto(Long restaurantId, Long productId, ProductPhotoInput productPhotoInput)
			throws IOException;

	@ApiOperation("Excluir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/produto inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/produto não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	void delete(Long restaurantId, Long productId);

}