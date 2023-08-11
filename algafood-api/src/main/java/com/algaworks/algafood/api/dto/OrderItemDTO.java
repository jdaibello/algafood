package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO extends RepresentationModel<OrderItemDTO> {

    @Schema(example = "1", required = true)
    private Long productId;

    @Schema(example = "X-Tudo", required = true)
    private String productName;

    @Schema(example = "2", required = true)
    private Integer quantity;

    @Schema(example = "32.00", required = true)
    private BigDecimal unitPrice;

    @Schema(example = "64.00", required = true)
    private BigDecimal totalPrice;

    @Schema(example = "Sem alface")
    private String note;
}
