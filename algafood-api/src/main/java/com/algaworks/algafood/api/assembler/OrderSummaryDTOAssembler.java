package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.domain.model.Order;

@Component
public class OrderSummaryDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;

	public OrderSummaryDTO toModel(Order order) {
		return modelMapper.map(order, OrderSummaryDTO.class);
	}

	public List<OrderSummaryDTO> toCollectionModel(List<Order> orders) {
		return orders.stream().map(order -> toModel(order)).collect(Collectors.toList());
	}
}
