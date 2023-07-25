package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.api.dto.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KitchenDTO {

    @ApiModelProperty(example = "1", required = true)
    @JsonView(RestaurantView.Summary.class)
    private Long id;

    @ApiModelProperty(example = "Brasileira", required = true)
    @JsonView(RestaurantView.Summary.class)
    private String name;
}
