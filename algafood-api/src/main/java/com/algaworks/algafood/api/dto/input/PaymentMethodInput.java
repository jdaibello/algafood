package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodInput {

	@ApiModelProperty(example = "Vale Refeição", required = true)
	@NotBlank
	private String description;
}
