package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.api.dto.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestaurantDTO {

	@JsonView(RestaurantView.Summary.class)
	private Long id;

	@JsonView(RestaurantView.Summary.class)
	private String name;

	@JsonView(RestaurantView.Summary.class)
	private BigDecimal shippingFee;

	@JsonView(RestaurantView.Summary.class)
	private KitchenDTO kitchen;

	private Boolean active;
	private Boolean opened;
	private AddressDTO address;
}
