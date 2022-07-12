package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CityDTO {
	private Long id;
	private String name;
	private StateDTO state;
}
