package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantDTO {
	private Long id;
	private String name;
	private BigDecimal shippingFee;
	private KitchenDTO kitchenId;
}
