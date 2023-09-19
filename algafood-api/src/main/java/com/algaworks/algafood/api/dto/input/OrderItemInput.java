package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Getter
@Setter
public class OrderItemInput {

    @Schema(example = "1", required = true)
    @NotNull
    private Long productId;

    @Schema(example = "1", required = true)
    @NotNull
    @PositiveOrZero
    private Integer quantity;

    @Schema(example = "")
    private String note;
}
