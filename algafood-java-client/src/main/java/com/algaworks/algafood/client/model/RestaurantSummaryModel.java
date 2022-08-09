package com.algaworks.algafood.client.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestaurantSummaryModel {
	private Long id;
	private String name;
	private BigDecimal shippingFee;
	private KitchenModel kitchen;
}
