package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StateDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "São Paulo")
	private String name;
}
