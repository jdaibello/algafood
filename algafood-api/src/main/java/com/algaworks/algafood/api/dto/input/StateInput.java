package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateInput {

	@ApiModelProperty(example = "Rio Grande do Sul")
	@NotBlank
	private String name;
}
