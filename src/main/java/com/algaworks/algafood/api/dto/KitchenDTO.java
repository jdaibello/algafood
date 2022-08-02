package com.algaworks.algafood.api.dto;

import com.algaworks.algafood.api.dto.view.RestaurantView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KitchenDTO {

	@JsonView(RestaurantView.Summary.class)
	private Long id;

	@JsonView(RestaurantView.Summary.class)
	private String name;
}
