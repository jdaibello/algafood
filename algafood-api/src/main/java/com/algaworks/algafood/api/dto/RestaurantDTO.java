package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantDTO extends RepresentationModel<RestaurantDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Humberto Lanches", required = true)
    private String name;

    @ApiModelProperty(example = "0.00", required = true)
    private BigDecimal shippingFee;

    private KitchenDTO kitchen;

    @ApiModelProperty(example = "true", required = true)
    private Boolean active;

    @ApiModelProperty(example = "true", required = true)
    private Boolean opened;

    @ApiModelProperty(required = true)
    private AddressDTO address;
}
