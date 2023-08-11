package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @Schema(example = "Marguerita", required = true)
    @NotBlank
    private String name;

    @Schema(example = "Molho, mussarela, tomate seco e manjericão", required = true)
    @NotBlank
    private String description;

    @Schema(example = "50.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    @Schema(example = "true", required = true)
    @NotNull
    private Boolean active;
}
