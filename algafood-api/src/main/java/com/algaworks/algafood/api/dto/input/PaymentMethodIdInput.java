package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentMethodIdInput {

    @Schema(example = "1", required = true)
    @NotNull
    private Long id;
}
