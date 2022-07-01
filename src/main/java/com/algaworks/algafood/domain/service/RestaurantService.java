package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {
	private static final String RESTAURANT_IN_USE_MSG = "Restaurante de código %d não pode ser removido, pois está em uso";

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenService kitchenService;

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen = kitchenService.findOrFail(kitchenId);

		restaurant.setKitchen(kitchen);
		return restaurantRepository.save(restaurant);
	}

	@Transactional
	public void delete(Long restaurantId) {
		try {
			restaurantRepository.deleteById(restaurantId);

		} catch (EmptyResultDataAccessException e) {
			throw new RestaurantNotFoundException(restaurantId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(RESTAURANT_IN_USE_MSG, restaurantId));
		}
	}

	public Restaurant findOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
}
