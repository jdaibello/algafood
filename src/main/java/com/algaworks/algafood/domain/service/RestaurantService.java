package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private KitchenService kitchenService;

	@Autowired
	private CityService cityService;

	@Autowired
	private PaymentMethodService paymentMethodService;

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Long cityId = restaurant.getAddress().getCity().getId();

		Kitchen kitchen = kitchenService.findOrFail(kitchenId);
		City city = cityService.findOrFail(cityId);

		restaurant.setKitchen(kitchen);
		restaurant.getAddress().setCity(city);
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

	@Transactional
	public void open(Long restaurantId) {
		Restaurant currentRestaurant = findOrFail(restaurantId);
		currentRestaurant.open();
	}

	@Transactional
	public void close(Long restaurantId) {
		Restaurant currentRestaurant = findOrFail(restaurantId);
		currentRestaurant.close();
	}

	@Transactional
	public void attachPaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findOrFail(restaurantId);
		PaymentMethod paymentMethod = paymentMethodService.findOrFail(paymentMethodId);

		restaurant.addPaymentMethod(paymentMethod);
	}

	@Transactional
	public void detachPaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findOrFail(restaurantId);
		PaymentMethod paymentMethod = paymentMethodService.findOrFail(paymentMethodId);

		restaurant.removePaymentMethod(paymentMethod);
	}

	public Restaurant findOrFail(Long restaurantId) {
		return restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}
}
