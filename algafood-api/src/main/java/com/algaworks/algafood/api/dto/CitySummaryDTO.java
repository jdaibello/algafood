package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CitySummaryDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Fortaleza")
	private String name;

	@ApiModelProperty(example = "Ceará")
	private String state;
}
