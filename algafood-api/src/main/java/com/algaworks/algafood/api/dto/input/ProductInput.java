package com.algaworks.algafood.api.dto.input;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInput {

	@ApiModelProperty(example = "Marguerita", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(example = "Molho, mussarela, tomate seco e manjericão", required = true)
	@NotBlank
	private String description;

	@ApiModelProperty(example = "50.00", required = true)
	@NotNull
	@PositiveOrZero
	private BigDecimal price;

	@ApiModelProperty(example = "true", required = true)
	@NotNull
	private Boolean active;
}
