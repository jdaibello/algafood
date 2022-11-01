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
	@GetMapping("/{restaurantId}")
	public RestaurantDTO find(@PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		return restaurantDTOAssembler.toModel(restaurant);
	}

	@ApiOperation("Adicionar")
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
	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant currentRestaurant = service.findOrFail(restaurantId);
			restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);

			return restaurantDTOAssembler.toModel(service.save(currentRestaurant));
		} catch (KitchenNotFoundException | CityNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation("Ativar")
	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restaurantId) {
		service.activate(restaurantId);
	}

	@ApiOperation("Desativar")
	@DeleteMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivate(@PathVariable Long restaurantId) {
		service.inactivate(restaurantId);
	}

	@ApiOperation("Ativar vários")
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
	@PutMapping("/{restaurantId}/opening")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void open(@PathVariable Long restaurantId) {
		service.open(restaurantId);
	}

	@ApiOperation("Fechar")
	@PutMapping("/{restaurantId}/closing")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void close(@PathVariable Long restaurantId) {
		service.close(restaurantId);
	}
}
