package com.algaworks.algafood.api.dto.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GroupInput {

    @ApiModelProperty(example = "Cozinheiros", required = true)
    @NotBlank
    private String name;
}
