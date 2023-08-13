package com.algaworks.algafood.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.ClientApiException;
import com.algaworks.algafood.client.api.RestaurantClient;

@Slf4j
public class RestaurantListingMain {

	public static void main(String[] args) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			RestaurantClient restaurantClient = new RestaurantClient(restTemplate, "http://localhost:8080");

			restaurantClient.fetchAll().stream().forEach(restaurant -> System.out.println(restaurant));
		} catch (ClientApiException e) {
			if (e.getProblem() != null) {
				log.error(e.getProblem().toString());
				log.error(e.getProblem().getUserMessage());
			} else {
				log.error("Erro desconhecido");
				log.error(e.getMessage());
			}
		}
	}
}
