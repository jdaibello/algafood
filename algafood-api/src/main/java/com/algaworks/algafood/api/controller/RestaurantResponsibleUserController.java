package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UserDTOAssembler;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Responsáveis pelos Restaurantes")
@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsibles")
public class RestaurantResponsibleUserController {

	@Autowired
	private RestaurantService service;

	@Autowired
	private UserDTOAssembler userDTOAssembler;

	@ApiOperation("Listar")
	@GetMapping
	public List<UserDTO> fetchAll(
			@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		return userDTOAssembler.toCollectionModel(restaurant.getResponsibles());
	}

	@ApiOperation("Anexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante e usuário anexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/usuário inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/usuário não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		service.attachResponsible(restaurantId, userId);
	}

	@ApiOperation("Desanexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante e usuário desanexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante/usuário inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante/usuário não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		service.detachResponsible(restaurantId, userId);
	}
}
