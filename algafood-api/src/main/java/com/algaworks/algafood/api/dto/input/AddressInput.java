package com.algaworks.algafood.api.dto.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressInput {

	@ApiModelProperty(example = "02542-121", required = true)
	@NotBlank
	private String zipCode;

	@ApiModelProperty(example = "Rua das Maças", required = true)
	@NotBlank
	private String street;

	@ApiModelProperty(example = "125", required = true)
	@NotBlank
	private String number;

	@ApiModelProperty(example = "")
	private String complement;

	@ApiModelProperty(example = "Bairro Novo Horizonte", required = true)
	@NotBlank
	private String district;

	@Valid
	@NotNull
	private CityIdInput city;
}
