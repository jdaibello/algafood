package com.algaworks.algafood.client;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestaurantClient;

public class RestaurantListingMain {

	public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			RestaurantClient restaurantClient = new RestaurantClient(restTemplate, "http://localhost:8080");

			restaurantClient.fetchAll().stream().forEach(restaurant -> System.out.println(restaurant));
		} catch (ClientApiException e) {
			if (e.getProblem() != null) {
				System.out.println(e.getProblem());
				System.out.println(e.getProblem().getUserMessage());
			} else {
				System.out.println("Erro desconhecido");
				e.printStackTrace();
			}
		}
	}
}
