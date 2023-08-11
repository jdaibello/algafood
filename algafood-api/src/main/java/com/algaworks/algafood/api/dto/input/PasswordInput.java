package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordInput {

    @Schema(example = "ABCDEFGH", required = true)
    @NotBlank
    private String currentPassword;

    @Schema(example = "12345678", required = true)
    @NotBlank
    private String newPassword;
}
