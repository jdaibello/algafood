package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "123e4567-e89b-12d3-a456-426655440000", required = true)
    private String code;

    @Schema(example = "170.00", required = true)
    private BigDecimal subtotal;

    @Schema(example = "4.99", required = true)
    private BigDecimal shippingFee;

    @Schema(example = "174.99", required = true)
    private BigDecimal totalValue;

    @Schema(example = "Confirmado", required = true)
    private String status;

    @Schema(required = true)
    private OffsetDateTime creationDate;

    private OffsetDateTime confirmationDate;

    @Schema(example = "null")
    private OffsetDateTime deliveryDate;

    @Schema(example = "null")
    private OffsetDateTime cancellationDate;

    @Schema(required = true)
    private RestaurantOnlyNameDTO restaurant;

    @Schema(required = true)
    private UserDTO client;

    @Schema(required = true)
    private PaymentMethodDTO paymentMethod;

    @Schema(required = true)
    private AddressDTO deliveryAddress;

    @Schema(required = true)
    private List<OrderItemDTO> items;
}
