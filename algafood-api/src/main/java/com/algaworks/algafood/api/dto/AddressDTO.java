package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

	@ApiModelProperty(example = "02542-120")
	private String zipCode;

	@ApiModelProperty(example = "Rua das Laranjas")
	private String street;

	@ApiModelProperty(example = "123")
	private String number;

	@ApiModelProperty(example = "Bloco C")
	private String complement;

	@ApiModelProperty(example = "Bairro Novo Horizonte")
	private String district;

	private CitySummaryDTO city;
}
