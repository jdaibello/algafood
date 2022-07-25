package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.dto.AddressDTO;
import com.algaworks.algafood.domain.model.Address;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		var addressToAddressDTOTypeMap = modelMapper.createTypeMap(Address.class, AddressDTO.class);
		addressToAddressDTOTypeMap.<String>addMapping(addressSrc -> addressSrc.getCity().getState().getName(),
				(addressDTODest, value) -> addressDTODest.getCity().setState(value));

		return modelMapper;
	}
}
