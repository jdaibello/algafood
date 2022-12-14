package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateDTO {

	@ApiModelProperty(example = "1", required = true)
	private Long id;

	@ApiModelProperty(example = "São Paulo", required = true)
	private String name;
}
