package com.algaworks.algafood.api.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "X-Tudo")
	private String name;

	@ApiModelProperty(example = "Pão de hambúrguer, hambúrguer caseiro, contra-filé, frango, bacon, calabresa, " +
			"catupiry, ovo, presunto, mussarela, milho, tomate, alface e batata palha")
	private String description;

	@ApiModelProperty(example = "32.00")
	private BigDecimal price;

	@ApiModelProperty(example = "true")
	private Boolean active;
}
