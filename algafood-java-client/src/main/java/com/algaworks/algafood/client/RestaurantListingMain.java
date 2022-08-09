package com.algaworks.algafood.client;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.RestaurantClient;

public class RestaurantListingMain {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		RestaurantClient restaurantClient = new RestaurantClient(restTemplate, "http://localhost:8080");

		restaurantClient.fetchAll().stream().forEach(restaurant -> System.out.println(restaurant));
	}
}
