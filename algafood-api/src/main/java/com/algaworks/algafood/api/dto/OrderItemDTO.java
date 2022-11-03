package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

	@ApiModelProperty(example = "1")
	private Long productId;

	@ApiModelProperty(example = "X-Tudo")
	private String productName;

	@ApiModelProperty(example = "2")
	private Integer quantity;

	@ApiModelProperty(example = "32.00")
	private BigDecimal unitPrice;

	@ApiModelProperty(example = "64.00")
	private BigDecimal totalPrice;

	@ApiModelProperty(example = "Sem alface")
	private String note;
}
