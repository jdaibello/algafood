package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
public class CityInput {

    @Schema(example = "Guarulhos", required = true)
    @NotBlank
    private String name;

    @Valid
    @NotNull
    private StateIdInput state;
}
