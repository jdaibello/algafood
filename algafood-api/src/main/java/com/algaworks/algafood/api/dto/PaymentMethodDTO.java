package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDTO {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "PIX", required = true)
    private String description;
}
