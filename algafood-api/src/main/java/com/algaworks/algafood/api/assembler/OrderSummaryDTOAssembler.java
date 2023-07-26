package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.controller.UserController;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderSummaryDTOAssembler extends RepresentationModelAssemblerSupport<Order, OrderSummaryDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public OrderSummaryDTOAssembler() {
        super(OrderController.class, OrderSummaryDTO.class);
    }

    @Override
    public OrderSummaryDTO toModel(Order order) {
        OrderSummaryDTO orderSummaryDTO = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderSummaryDTO);

        orderSummaryDTO.add(linkTo(OrderController.class).withRel("orders"));

        orderSummaryDTO.getRestaurant().add(linkTo(methodOn(RestaurantController.class).find(order.getRestaurant()
                .getId())).withSelfRel());

        orderSummaryDTO.getClient().add(linkTo(methodOn(UserController.class).find(order.getClient().getId()))
                .withSelfRel());

        return orderSummaryDTO;
    }
}
