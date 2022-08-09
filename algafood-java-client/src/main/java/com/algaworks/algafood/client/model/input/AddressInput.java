package com.algaworks.algafood.client.model.input;

import lombok.Data;

@Data
public class AddressInput {
	private String zipCode;
	private String street;
	private String number;
	private String complement;
	private String district;
	private CityIdInput city;
}
