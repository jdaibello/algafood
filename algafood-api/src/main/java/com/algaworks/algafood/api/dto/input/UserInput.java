package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInput {

    @ApiModelProperty(example = "Jonathan Pereira", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "jonathan.pereira@outlook.com", required = true)
    @NotBlank
    @Email
    private String email;
}
