package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {

	@ApiModelProperty(example = "123e4567-e89b-12d3-a456-426655440000")
	private String code;

	@ApiModelProperty(example = "170.00")
	private BigDecimal subtotal;

	@ApiModelProperty(example = "4.99")
	private BigDecimal shippingFee;

	@ApiModelProperty(example = "174.99")
	private BigDecimal totalValue;

	@ApiModelProperty(example = "Confirmado")
	private String status;

	private OffsetDateTime creationDate;
	private OffsetDateTime confirmationDate;

	// TODO not working
	@ApiModelProperty(example = "null")
	private OffsetDateTime deliveryDate;

	// TODO not working
	@ApiModelProperty(example = "null")
	private OffsetDateTime cancellationDate;

	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
	private PaymentMethodDTO paymentMethod;
	private AddressDTO deliveryAddress;
	private List<OrderItemDTO> items;
}
