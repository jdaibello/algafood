package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.KitchenDTO;
import com.algaworks.algafood.api.model.RestaurantDTO;
import com.algaworks.algafood.domain.model.Restaurant;

@Component
public class RestaurantModelAssembler {

	public RestaurantDTO toModel(Restaurant restaurant) {
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

	public List<RestaurantDTO> toCollectionModel(List<Restaurant> restaurants) {
		return restaurants.stream().map(restaurant -> toModel(restaurant)).collect(Collectors.toList());
	}
}
