package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public OrderDTO toModel(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public List<OrderDTO> toCollectionModel(List<Order> orders) {
        return orders.stream().map(order -> toModel(order)).collect(Collectors.toList());
    }
}
