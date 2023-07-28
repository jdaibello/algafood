package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.*;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDTOAssembler extends RepresentationModelAssemblerSupport<Order, OrderDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public OrderDTOAssembler() {
        super(OrderController.class, OrderDTO.class);
    }

    @Override
    public OrderDTO toModel(Order order) {
        OrderDTO orderDTO = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderDTO);

        orderDTO.add(algaLinks.linkToOrders());

        orderDTO.getRestaurant().add(linkTo(methodOn(RestaurantController.class).find(order.getRestaurant()
                .getId())).withSelfRel());

        orderDTO.getClient().add(linkTo(methodOn(UserController.class).find(order.getClient().getId()))
                .withSelfRel());

        orderDTO.getPaymentMethod().add(linkTo(methodOn(PaymentMethodController.class).find(order.getPaymentMethod()
                .getId(), null)).withSelfRel());

        orderDTO.getDeliveryAddress().getCity().add(linkTo(methodOn(CityController.class).find(order.getDeliveryAddress()
                .getCity().getId())).withSelfRel());

        orderDTO.getItems().forEach(item -> {
            item.add(linkTo(methodOn(RestaurantProductController.class).find(orderDTO.getRestaurant().getId(),
                    item.getProductId())).withRel("product"));
        });

        return orderDTO;
    }
}
