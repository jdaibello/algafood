package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class KitchenInput {

    @ApiModelProperty(example = "Italiana", required = true)
    @NotBlank
    private String name;
}
