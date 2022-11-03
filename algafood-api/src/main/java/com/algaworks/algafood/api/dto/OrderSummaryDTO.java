package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSummaryDTO {

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
	private RestaurantSummaryDTO restaurant;
	private UserDTO client;
}
