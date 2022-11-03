package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInput {

	@ApiModelProperty(example = "Jonathan Pereira")
	@NotBlank
	private String name;

	@ApiModelProperty(example = "jonathan.pereira@outlook.com")
	@NotBlank
	@Email
	private String email;
}
