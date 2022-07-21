package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.input.CityInput;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;

@Component
public class CityInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public City toDomainObject(CityInput cityInput) {
		return modelMapper.map(cityInput, City.class);
	}

	public void copyToDomainObject(CityInput cityInput, City city) {

		// To avoid "org.springframework.orm.jpa.JpaSystemException: identifier of an
		// instance of com.algaworks.algafood.domain.model.Kitchen was altered from X to
		// Y" error;
		city.setState(new State());

		modelMapper.map(cityInput, city);
	}
}
