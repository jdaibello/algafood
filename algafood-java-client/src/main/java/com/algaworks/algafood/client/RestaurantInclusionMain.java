package com.algaworks.algafood.client;

import java.math.BigDecimal;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestaurantClient;
import com.algaworks.algafood.client.model.RestaurantModel;
import com.algaworks.algafood.client.model.input.AddressInput;
import com.algaworks.algafood.client.model.input.CityIdInput;
import com.algaworks.algafood.client.model.input.KitchenIdInput;
import com.algaworks.algafood.client.model.input.RestaurantInput;

public class RestaurantInclusionMain {

	public static void main(String[] args) {
		try {
			var restTemplate = new RestTemplate();
			var restaurantClient = new RestaurantClient(restTemplate, "http://localhost:8080");

			var kitchen = new KitchenIdInput();
			kitchen.setId(1L);

			var city = new CityIdInput();
			city.setId(1L);

			var address = new AddressInput();
			address.setCity(city);
			address.setZipCode("38500-111");
			address.setStreet("Rua Xyz");
			address.setNumber("300");
			address.setDistrict("Centro");

			var restaurant = new RestaurantInput();
			restaurant.setName("Comida Mineira");
			restaurant.setShippingFee(new BigDecimal(9.5));
			restaurant.setKitchen(new KitchenIdInput());
			restaurant.setKitchen(kitchen);
			restaurant.setAddress(address);

			RestaurantModel restaurantModel = restaurantClient.add(restaurant);

			System.out.println(restaurantModel);
		} catch (ClientApiException e) {
			if (e.getProblem() != null) {
				System.out.println(e.getProblem().getUserMessage());
				e.getProblem().getObjects().stream().forEach(p -> System.out.println("- " + p.getUserMessage()));
			} else {
				System.out.println("Erro desconhecido");
				e.printStackTrace();
			}
		}
	}
}
