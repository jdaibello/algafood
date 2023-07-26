package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDTO extends RepresentationModel<OrderItemDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long productId;

    @ApiModelProperty(example = "X-Tudo", required = true)
    private String productName;

    @ApiModelProperty(example = "2", required = true)
    private Integer quantity;

    @ApiModelProperty(example = "32.00", required = true)
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "64.00", required = true)
    private BigDecimal totalPrice;

    @ApiModelProperty(example = "Sem alface")
    private String note;
}
