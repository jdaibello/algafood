package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryDTO {
	private String code;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalValue;
	private String status;
	private OffsetDateTime creationDate;
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
}
