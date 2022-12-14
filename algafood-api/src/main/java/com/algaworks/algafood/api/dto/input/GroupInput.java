package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupInput {

	@ApiModelProperty(example = "Cozinheiros", required = true)
	@NotBlank
	private String name;
}
