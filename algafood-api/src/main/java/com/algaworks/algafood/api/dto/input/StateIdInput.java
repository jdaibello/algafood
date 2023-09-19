package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Setter
@Getter
public class StateIdInput {

    @Schema(example = "1", required = true)
    @NotNull
    private Long id;
}
