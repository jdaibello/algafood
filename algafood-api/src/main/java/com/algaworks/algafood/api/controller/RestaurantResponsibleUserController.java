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
import com.algaworks.algafood.api.controller.openapi.RestaurantResponsibleUserControllerOpenApi;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsibles")
public class RestaurantResponsibleUserController implements RestaurantResponsibleUserControllerOpenApi {

	@Autowired
	private RestaurantService service;

	@Autowired
	private UserDTOAssembler userDTOAssembler;

	@Override
	@GetMapping
	public List<UserDTO> fetchAll(
			@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		return userDTOAssembler.toCollectionModel(restaurant.getResponsibles());
	}

	@Override
	@PutMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		service.attachResponsible(restaurantId, userId);
	}

	@Override
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		service.detachResponsible(restaurantId, userId);
	}
}
