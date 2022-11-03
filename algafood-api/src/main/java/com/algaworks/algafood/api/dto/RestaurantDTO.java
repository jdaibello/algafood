package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.api.dto.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestaurantDTO {

	@ApiModelProperty(example = "1")
	@JsonView(RestaurantView.Summary.class)
	private Long id;

	@ApiModelProperty(example = "Humberto Lanches")
	@JsonView(RestaurantView.Summary.class)
	private String name;

	@ApiModelProperty(example = "0.00")
	@JsonView(RestaurantView.Summary.class)
	private BigDecimal shippingFee;

	@JsonView(RestaurantView.Summary.class)
	private KitchenDTO kitchen;

	@ApiModelProperty(example = "true")
	private Boolean active;

	@ApiModelProperty(example = "true")
	private Boolean opened;

	private AddressDTO address;
}
