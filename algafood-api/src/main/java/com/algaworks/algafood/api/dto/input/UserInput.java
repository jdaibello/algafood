package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserInput {

    @Schema(example = "Jonathan Pereira", required = true)
    @NotBlank
    private String name;

    @Schema(example = "jonathan.pereira@outlook.com", required = true)
    @NotBlank
    @Email
    private String email;
}
