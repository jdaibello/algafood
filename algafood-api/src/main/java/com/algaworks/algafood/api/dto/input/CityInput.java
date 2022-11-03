package com.algaworks.algafood.api.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityInput {

	@ApiModelProperty(example = "Guarulhos")
	@NotBlank
	private String name;

	@Valid
	@NotNull
	private StateIdInput state;
}
