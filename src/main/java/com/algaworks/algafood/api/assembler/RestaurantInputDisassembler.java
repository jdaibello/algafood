package com.algaworks.algafood.api.assembler;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestaurantInput;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {

	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantInput.getName());
		restaurant.setShippingFee(restaurantInput.getShippingFee());

		Kitchen kitchen = new Kitchen();
		kitchen.setId(restaurantInput.getKitchen().getId());

		restaurant.setKitchen(kitchen);

		return restaurant;
	}
}
