package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Garçons")
	private String name;
}
