package com.algaworks.algafood.api.dto.input;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithPasswordInput extends UserInput {

    @Schema(example = "abcdefgh", required = true)
    @NotBlank
    private String password;
}
