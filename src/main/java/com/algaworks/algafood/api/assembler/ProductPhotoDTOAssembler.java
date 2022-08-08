package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.ProductPhotoDTO;
import com.algaworks.algafood.domain.model.ProductPhoto;

@Component
public class ProductPhotoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProductPhotoDTO toModel(ProductPhoto photo) {
		return modelMapper.map(photo, ProductPhotoDTO.class);
	}
}
