package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.ProductDTO;
import com.algaworks.algafood.domain.model.Product;

@Component
public class ProductDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public ProductDTO toModel(Product product) {
		return modelMapper.map(product, ProductDTO.class);
	}

	public List<ProductDTO> toCollectionModel(List<Product> products) {
		return products.stream().map(product -> toModel(product)).collect(Collectors.toList());
	}
}
