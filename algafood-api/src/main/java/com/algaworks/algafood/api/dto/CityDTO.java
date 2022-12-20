package com.algaworks.algafood.api.dto;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityDTO extends RepresentationModel<CityDTO> {

	@ApiModelProperty(example = "1", required = true)
	private Long id;

	@ApiModelProperty(example = "São Paulo", required = true)
	private String name;

	@ApiModelProperty(required = true)
	private StateDTO state;
}
