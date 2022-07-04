package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.api.model.KitchenDTO;
import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.api.model.input.RestaurantInput;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantService service;

	@GetMapping
	public List<RestaurantDTO> fetchAll() {
		return toCollectionModel(restaurantRepository.findAll());
	}

	@GetMapping("/{restaurantId}")
	public RestaurantDTO find(@PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		return toModel(restaurant);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestaurantDTO add(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = toDomainObject(restaurantInput);

			return toModel(service.save(restaurant));
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restaurantId}")
	public RestaurantDTO update(@PathVariable Long restaurantId, @RequestBody RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = toDomainObject(restaurantInput);
			Restaurant currentRestaurant = service.findOrFail(restaurantId);

			BeanUtils.copyProperties(restaurant, currentRestaurant, "id", "paymentMethods", "address", "creationDate",
					"products");

			return toModel(service.save(currentRestaurant));
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{restaurantId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restaurantId) {
		service.delete(restaurantId);
	}

	private RestaurantDTO toModel(Restaurant restaurant) {
		KitchenDTO kitchenDto = new KitchenDTO();
		kitchenDto.setId(restaurant.getKitchen().getId());
		kitchenDto.setName(restaurant.getKitchen().getName());

		RestaurantDTO restaurantDto = new RestaurantDTO();
		restaurantDto.setId(restaurant.getId());
		restaurantDto.setName(restaurant.getName());
		restaurantDto.setShippingFee(restaurant.getShippingFee());
		restaurantDto.setKitchen(kitchenDto);
		return restaurantDto;
	}

	private List<RestaurantDTO> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream().map(restaurant -> toModel(restaurant)).collect(Collectors.toList());
	}

	private Restaurant toDomainObject(RestaurantInput restaurantInput) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantInput.getName());
		restaurant.setShippingFee(restaurantInput.getShippingFee());

		Kitchen kitchen = new Kitchen();
		kitchen.setId(restaurantInput.getKitchen().getId());

		restaurant.setKitchen(kitchen);

		return restaurant;
	}
}
