package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @Schema(example = "02542-121", required = true)
    @NotBlank
    private String zipCode;

    @Schema(example = "Rua das Maças", required = true)
    @NotBlank
    private String street;

    @Schema(example = "125", required = true)
    @NotBlank
    private String number;

    @Schema(example = "")
    private String complement;

    @Schema(example = "Bairro Novo Horizonte", required = true)
    @NotBlank
    private String district;

    @Valid
    @NotNull
    private CityIdInput city;
}
