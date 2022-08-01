package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Order toDomainObject(OrderInput orderInput) {
        return modelMapper.map(orderInput, Order.class);
    }

    public void copyToDomainObject(OrderInput orderInput, Order order) {
        modelMapper.map(orderInput, order);
    }
}
