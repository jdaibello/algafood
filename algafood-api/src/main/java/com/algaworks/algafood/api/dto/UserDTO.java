package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	@ApiModelProperty(example = "1", required = true)
	private Long id;

	@ApiModelProperty(example = "Gustavo da Silva", required = true)
	private String name;

	@ApiModelProperty(example = "gustavosilva@gmail.com", required = true)
	private String email;
}
