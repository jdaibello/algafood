package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.core.validation.ValidationException;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private RestaurantService service;

	@Autowired
	private SmartValidator validator;

	@GetMapping
	public List<Restaurant> fetchAll() {
		return restaurantRepository.findAll();
	}

	@GetMapping("/{restaurantId}")
	public RestaurantDTO find(@PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		RestaurantDTO restaurantDTO = null;

		return restaurantDTO;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant add(@RequestBody @Valid Restaurant restaurant) {
		try {
			return service.save(restaurant);
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restaurantId}")
	public Restaurant update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
		Restaurant currentRestaurant = service.findOrFail(restaurantId);

		BeanUtils.copyProperties(restaurant, currentRestaurant, "id", "paymentMethods", "address", "creationDate",
				"products");

		try {
			return service.save(currentRestaurant);
		} catch (KitchenNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PatchMapping("/{restaurantId}")
	public Restaurant partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields,
			HttpServletRequest request) {

		Restaurant currentRestaurant = service.findOrFail(restaurantId);
		merge(fields, currentRestaurant, request);
		validate(currentRestaurant, "restaurant");

		return update(restaurantId, currentRestaurant);
	}

	@DeleteMapping("/{restaurantId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long restaurantId) {
		service.delete(restaurantId);
	}

	private void validate(Restaurant restaurant, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurant, objectName);
		validator.validate(restaurant, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
	}

	private void merge(Map<String, Object> originData, Restaurant restaurantDestiny, HttpServletRequest request) {
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

			Restaurant originRestaurant = objectMapper.convertValue(originData, Restaurant.class);

			originData.forEach((name, value) -> {
				Field field = ReflectionUtils.findField(Restaurant.class, name);
				field.setAccessible(true);

				Object newValue = ReflectionUtils.getField(field, originRestaurant);
				ReflectionUtils.setField(field, restaurantDestiny, newValue);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
}
