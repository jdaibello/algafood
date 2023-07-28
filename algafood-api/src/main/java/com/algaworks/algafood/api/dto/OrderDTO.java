package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "orders")
@Getter
@Setter
public class OrderDTO extends RepresentationModel<OrderDTO> {

    @ApiModelProperty(example = "123e4567-e89b-12d3-a456-426655440000", required = true)
    private String code;

    @ApiModelProperty(example = "170.00", required = true)
    private BigDecimal subtotal;

    @ApiModelProperty(example = "4.99", required = true)
    private BigDecimal shippingFee;

    @ApiModelProperty(example = "174.99", required = true)
    private BigDecimal totalValue;

    @ApiModelProperty(example = "Confirmado", required = true)
    private String status;

    @ApiModelProperty(required = true)
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;

    @ApiModelProperty(example = "null")
    private OffsetDateTime deliveryDate;

    @ApiModelProperty(example = "null")
    private OffsetDateTime cancellationDate;

    @ApiModelProperty(required = true)
    private RestaurantOnlyNameDTO restaurant;

    @ApiModelProperty(required = true)
    private UserDTO client;

    @ApiModelProperty(required = true)
    private PaymentMethodDTO paymentMethod;

    @ApiModelProperty(required = true)
    private AddressDTO deliveryAddress;

    @ApiModelProperty(required = true)
    private List<OrderItemDTO> items;
}
