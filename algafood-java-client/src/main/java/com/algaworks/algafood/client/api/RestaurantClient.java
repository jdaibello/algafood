package com.algaworks.algafood.client.api;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.model.RestaurantSummaryModel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RestaurantClient {
	private static final String RESOURCE_PATH = "/restaurantss";

	private RestTemplate restTemplate;
	private String url = "http://localhost:8080";

	public List<RestaurantSummaryModel> fetchAll() {
		try {
			URI resourceUrl = URI.create(url + RESOURCE_PATH);
			RestaurantSummaryModel[] restaurants = restTemplate.getForObject(resourceUrl,
					RestaurantSummaryModel[].class);

			return Arrays.asList(restaurants);
		} catch (RestClientResponseException e) {
			throw new ClientApiException(e.getMessage(), e);
		}
	}
}
