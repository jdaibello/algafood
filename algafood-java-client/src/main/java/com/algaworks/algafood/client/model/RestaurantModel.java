package com.algaworks.algafood.client.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestaurantModel {
	private Long id;
	private String name;
	private BigDecimal shippingFee;
	private Boolean active;
	private Boolean opened;
	private KitchenModel kitchen;
	private AddressModel address;
}
