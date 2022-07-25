package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.input.RestaurantInput;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Restaurant toDomainObject(RestaurantInput restaurantInput) {
		return modelMapper.map(restaurantInput, Restaurant.class);
	}

	public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {

		// To avoid "org.springframework.orm.jpa.JpaSystemException: identifier of an
		// instance of com.algaworks.algafood.domain.model.Kitchen was altered from X to
		// Y" error;
		restaurant.setKitchen(new Kitchen());

		if (restaurant.getAddress() != null) {
			restaurant.getAddress().setCity(new City());
		}

		modelMapper.map(restaurantInput, restaurant);
	}
}
