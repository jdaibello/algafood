package com.algaworks.algafood.api.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantInput {

    @Schema(example = "Pizzaria dos Devs", required = true)
    @NotBlank
    private String name;

    @Schema(example = "4.99", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal shippingFee;

    @Valid
    @NotNull
    private KitchenIdInput kitchen;

    @Valid
    @NotNull
    private AddressInput address;
}
