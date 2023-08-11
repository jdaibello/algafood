package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "paymentMethods")
@Getter
@Setter
public class PaymentMethodDTO extends RepresentationModel<PaymentMethodDTO> {

    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "PIX", required = true)
    private String description;
}
