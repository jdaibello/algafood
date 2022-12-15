package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestaurantDTOAssembler;
import com.algaworks.algafood.api.assembler.RestaurantInputDisassembler;
import com.algaworks.algafood.api.dto.RestaurantDTO;
import com.algaworks.algafood.api.dto.input.RestaurantInput;
import com.algaworks.algafood.api.dto.view.RestaurantView;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantService service;

	@Autowired
	private RestaurantDTOAssembler restaurantDTOAssembler;

	@Autowired
	private RestaurantInputDisassembler restaurantInputDisassembler;

	@ApiOperation("Listar")
	@JsonView(RestaurantView.Summary.class)
	@GetMapping
	public List<RestaurantDTO> fetchAll() {
		return restaurantDTOAssembler.toCollectionModel(restaurantRepository.findAll());
	}

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@GetMapping("/{restaurantId}")
	public RestaurantDTO find(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		return restaurantDTOAssembler.toModel(restaurant);
	}

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Restaurante cadastrado") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantDTO add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);

			return restaurantDTOAssembler.toModel(service.save(restaurant));
		} catch (KitchenNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant currentRestaurant = service.findOrFail(restaurantId);
			restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);

			return restaurantDTOAssembler.toModel(service.save(currentRestaurant));
		} catch (KitchenNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation("Ativar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante ativado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		service.activate(restaurantId);
	}

	@ApiOperation("Desativar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante desativado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivate(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		service.inactivate(restaurantId);
	}

	@ApiOperation("Ativar vários")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurantes ativados"),
			@ApiResponse(
				responseCode = "400",
				description = "IDs dos restaurantes inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurantes não encontrados",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping(value = "/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activateMultiples(@RequestBody List<Long> restaurantIds) {
		try {
			service.activate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation("Desativar vários")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurantes desativados"),
			@ApiResponse(
				responseCode = "400",
				description = "IDs dos restaurantes inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurantes não encontrados",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping(value = "/activations")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivateMultiples(@RequestBody List<Long> restaurantIds) {
		try {
			service.inactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation("Abrir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante aberto"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do restaurante inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{restaurantId}/opening")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void open(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		service.open(restaurantId);
	}

	@ApiOperation("Fechar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante fechado"),
			@ApiResponse(
				responseCode = "400",
				description = "IDs do restaurante inválidos",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Restaurante não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{restaurantId}/closing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void close(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		service.close(restaurantId);
	}
}
