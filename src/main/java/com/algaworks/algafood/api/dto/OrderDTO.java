package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
	private Long id;
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
