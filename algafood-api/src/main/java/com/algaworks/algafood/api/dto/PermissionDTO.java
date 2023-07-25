package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "ADICIONAR_RESTAURANTES", required = true)
    private String name;

    @ApiModelProperty(example = "Permite adicionar restaurantes", required = true)
    private String description;
}
