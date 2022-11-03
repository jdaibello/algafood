package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KitchenIdInput {

	@ApiModelProperty(example = "1")
	@NotNull
	private Long id;
}
