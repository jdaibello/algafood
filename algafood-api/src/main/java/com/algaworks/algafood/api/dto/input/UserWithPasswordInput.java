package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithPasswordInput extends UserInput {

    @ApiModelProperty(example = "abcdefgh", required = true)
    @NotBlank
    private String password;
}
