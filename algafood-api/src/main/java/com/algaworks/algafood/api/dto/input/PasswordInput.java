package com.algaworks.algafood.api.dto.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordInput {

	@ApiModelProperty(example = "ABCDEFGH")
	@NotBlank
	private String currentPassword;

	@ApiModelProperty(example = "12345678")
	@NotBlank
	private String newPassword;
}
