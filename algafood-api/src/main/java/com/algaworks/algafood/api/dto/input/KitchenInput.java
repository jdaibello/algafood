package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KitchenInput {

	@ApiModelProperty(example = "Italiana")
	@NotBlank
	private String name;
}
