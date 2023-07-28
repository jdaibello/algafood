package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

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
        orderDTO.add(algaLinks.linkToOrderConfirmation(order.getCode(), "confirm"));
        orderDTO.add(algaLinks.linkToOrderCancellation(order.getCode(), "cancel"));
        orderDTO.add(algaLinks.linkToOrderDelivery(order.getCode(), "delivery"));

        orderDTO.getRestaurant().add(algaLinks.linkToRestaurant(order.getRestaurant().getId()));
        orderDTO.getClient().add(algaLinks.linkToUser(order.getClient().getId()));
        orderDTO.getPaymentMethod().add(algaLinks.linkToPaymentMethod(order.getPaymentMethod().getId()));
        orderDTO.getDeliveryAddress().getCity().add(algaLinks.linkToCity(order.getDeliveryAddress().getCity().getId()));

        orderDTO.getItems().forEach(item -> {
            item.add(algaLinks.linkToProduct(orderDTO.getRestaurant().getId(), item.getProductId(), "product"));
        });

        return orderDTO;
    }
}
