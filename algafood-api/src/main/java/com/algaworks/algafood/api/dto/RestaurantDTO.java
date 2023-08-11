package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantDTO extends RepresentationModel<RestaurantDTO> {

    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "Humberto Lanches", required = true)
    private String name;

    @Schema(example = "0.00", required = true)
    private BigDecimal shippingFee;

    private KitchenDTO kitchen;

    @Schema(example = "true", required = true)
    private Boolean active;

    @Schema(example = "true", required = true)
    private Boolean opened;

    @Schema(required = true)
    private AddressDTO address;
}
