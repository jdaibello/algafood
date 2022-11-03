package com.algaworks.algafood.api.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantInput {

	@ApiModelProperty(example = "Pizzaria dos Devs")
	@NotBlank
	private String name;

	@ApiModelProperty(example = "4.99")
	@NotNull
	@PositiveOrZero
	private BigDecimal shippingFee;

	@Valid
	@NotNull
	private KitchenIdInput kitchen;

	@Valid
	@NotNull
	private AddressInput address;
}
