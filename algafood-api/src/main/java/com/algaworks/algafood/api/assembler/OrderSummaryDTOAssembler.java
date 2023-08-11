package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderSummaryDTOAssembler extends RepresentationModelAssemblerSupport<Order, OrderSummaryDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public OrderSummaryDTOAssembler() {
        super(OrderController.class, OrderSummaryDTO.class);
    }

    @Override
    public OrderSummaryDTO toModel(Order order) {
        OrderSummaryDTO orderSummaryDTO = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderSummaryDTO);

        if (algaSecurity.canSearchOrders()) {
            orderSummaryDTO.add(algaLinks.linkToOrders("orders"));
        }

        if (algaSecurity.canQueryRestaurants()) {
            orderSummaryDTO.getRestaurant().add(algaLinks.linkToRestaurant(order.getRestaurant().getId()));
        }

        if (algaSecurity.canQueryUsersGroupsPermissions()) {
            orderSummaryDTO.getClient().add(algaLinks.linkToUser(order.getClient().getId()));
        }

        return orderSummaryDTO;
    }
}
