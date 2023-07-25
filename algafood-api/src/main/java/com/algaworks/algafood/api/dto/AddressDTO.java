package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @ApiModelProperty(example = "02542-120", required = true)
    private String zipCode;

    @ApiModelProperty(example = "Rua das Laranjas", required = true)
    private String street;

    @ApiModelProperty(example = "123", required = true)
    private String number;

    @ApiModelProperty(example = "Bloco C")
    private String complement;

    @ApiModelProperty(example = "Bairro Novo Horizonte", required = true)
    private String district;

    @ApiModelProperty(required = true)
    private CitySummaryDTO city;
}
