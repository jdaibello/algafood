package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.OrderController;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    public OrderDTOAssembler() {
        super(OrderController.class, OrderDTO.class);
    }

    @Override
    public OrderDTO toModel(Order order) {
        OrderDTO orderDTO = createModelWithId(order.getCode(), order);
        modelMapper.map(order, orderDTO);

        if (algaSecurity.canSearchOrders()) {
            orderDTO.add(algaLinks.linkToOrders("orders"));
        }

        if (algaSecurity.canManageOrders(order.getCode())) {
            if (order.canBeConfirmed()) {
                orderDTO.add(algaLinks.linkToOrderConfirmation(order.getCode(), "confirm"));
            }

            if (order.canBeCancelled()) {
                orderDTO.add(algaLinks.linkToOrderCancellation(order.getCode(), "cancel"));
            }

            if (order.canBeDelivered()) {
                orderDTO.add(algaLinks.linkToOrderDelivery(order.getCode(), "delivery"));
            }
        }

        if (algaSecurity.canQueryRestaurants()) {
            orderDTO.getRestaurant().add(algaLinks.linkToRestaurant(order.getRestaurant().getId()));

            orderDTO.getItems().forEach(item -> {
                item.add(algaLinks.linkToProduct(orderDTO.getRestaurant().getId(), item.getProductId(), "product"));
            });
        }

        if (algaSecurity.canQueryUsersGroupsPermissions()) {
            orderDTO.getClient().add(algaLinks.linkToUser(order.getClient().getId()));
        }

        if (algaSecurity.canQueryPaymentMethods()) {
            orderDTO.getPaymentMethod().add(algaLinks.linkToPaymentMethod(order.getPaymentMethod().getId()));
        }

        if (algaSecurity.canQueryCities()) {
            orderDTO.getDeliveryAddress().getCity().add(algaLinks.linkToCity(order.getDeliveryAddress().getCity().getId()));
        }

        return orderDTO;
    }
}
