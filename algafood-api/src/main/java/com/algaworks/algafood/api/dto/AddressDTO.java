package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @Schema(example = "02542-120", required = true)
    private String zipCode;

    @Schema(example = "Rua das Laranjas", required = true)
    private String street;

    @Schema(example = "123", required = true)
    private String number;

    @Schema(example = "Bloco C")
    private String complement;

    @Schema(example = "Bairro Novo Horizonte", required = true)
    private String district;

    @Schema(required = true)
    private CitySummaryDTO city;
}
