package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class GroupInput {

    @Schema(example = "Cozinheiros", required = true)
    @NotBlank
    private String name;
}
