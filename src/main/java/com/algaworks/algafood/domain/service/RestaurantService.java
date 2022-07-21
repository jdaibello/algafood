package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {

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
	public void activate(Long restaurantId) {
		Restaurant currentRestaurant = findOrFail(restaurantId);
		currentRestaurant.activate();
	}

	@Transactional
	public void inactivate(Long restaurantId) {
		Restaurant currentRestaurant = findOrFail(restaurantId);
		currentRestaurant.inactivate();
	}

	public Restaurant findOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
}
