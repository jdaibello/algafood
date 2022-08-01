package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
	private String code;
	private BigDecimal subtotal;
	private BigDecimal shippingFee;
	private BigDecimal totalValue;
	private String status;
	private OffsetDateTime creationDate;
	private OffsetDateTime confirmationDate;
	private OffsetDateTime deliveryDate;
	private OffsetDateTime cancellationDate;
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
	private PaymentMethodDTO paymentMethod;
	private AddressDTO deliveryAddress;
	private List<OrderItemDTO> items;
}
