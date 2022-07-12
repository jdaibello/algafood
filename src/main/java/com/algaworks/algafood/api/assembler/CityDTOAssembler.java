package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CityDTO;
import com.algaworks.algafood.domain.model.City;

@Component
public class CityDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public CityDTO toModel(City city) {
		return modelMapper.map(city, CityDTO.class);
	}

	public List<CityDTO> toCollectionModel(List<City> cities) {
		return cities.stream().map(city -> toModel(city)).collect(Collectors.toList());
	}
}
