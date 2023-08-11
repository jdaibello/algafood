package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.ProductPhotoDTO;
import com.algaworks.algafood.api.dto.input.ProductPhotoInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Fotos dos Produtos dos Restaurantes")
public interface RestaurantProductPhotoControllerOpenApi {

    @Operation(
            summary = "Buscar por ID do restaurante e do produto")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/produto inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/produto não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ProductPhotoDTO find(Long restaurantId, Long productId);

    @Operation(summary = "Buscar por ID do restaurante e do produto")
    ResponseEntity<?> servePhoto(Long restaurantId, Long productId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Atualizar")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/produto inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/produto não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    ProductPhotoDTO updatePhoto(Long restaurantId, Long productId, ProductPhotoInput productPhotoInput,
                                @Parameter(
                                        description = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)",
                                        required = true) MultipartFile file)
            throws IOException;

    @Operation(summary = "Excluir")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
            @ApiResponse(
                    responseCode = "400",
                    description = "ID do restaurante/produto inválido(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Restaurante/produto não encontrado(s)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))})
    void delete(Long restaurantId, Long productId);

}