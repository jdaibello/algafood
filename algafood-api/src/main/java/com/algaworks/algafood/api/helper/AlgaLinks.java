package com.algaworks.algafood.api.helper;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {
    public static final TemplateVariables PAGE_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToOrders() {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("startCreationDate", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("endCreationDate", TemplateVariable.VariableType.REQUEST_PARAM));

        String ordersUrl = linkTo(OrderController.class).toUri().toString();

        return Link.of(UriTemplate.of(ordersUrl, PAGE_VARIABLES.concat(filterVariables)), "orders");
    }

    public Link linkToOrderConfirmation(String orderCode, String rel) {
        return linkTo(methodOn(OrderFlowController.class).confirm(orderCode)).withRel(rel);
    }

    public Link linkToOrderCancellation(String orderCode, String rel) {
        return linkTo(methodOn(OrderFlowController.class).cancel(orderCode)).withRel(rel);
    }

    public Link linkToOrderDelivery(String orderCode, String rel) {
        return linkTo(methodOn(OrderFlowController.class).delivery(orderCode)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class).find(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId) {
        return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUser(Long userId, String rel) {
        return linkTo(methodOn(UserController.class).find(userId)).withRel(rel);
    }

    public Link linkToUser(Long userId) {
        return linkToUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsers(String rel) {
        return linkTo(UserController.class).withRel(rel);
    }

    public Link linkToUsers() {
        return linkToUsers(IanaLinkRelations.SELF.value());
    }

    public Link linkToUserGroups(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class).fetchAll(userId)).withRel(rel);
    }

    public Link linkToUserGroups(Long userId) {
        return linkToUserGroups(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantResponsible(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantResponsibleUserController.class).fetchAll(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantResponsible(Long restaurantId) {
        return linkToRestaurantResponsible(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPaymentMethod(Long paymentMethodId, String rel) {
        return linkTo(methodOn(PaymentMethodController.class).find(paymentMethodId, null)).withRel(rel);
    }

    public Link linkToPaymentMethod(Long paymentMethodId) {
        return linkToPaymentMethod(paymentMethodId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCity(Long cityId, String rel) {
        return linkTo(methodOn(CityController.class).find(cityId)).withRel(rel);
    }

    public Link linkToCity(Long cityId) {
        return linkToCity(cityId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCities(String rel) {
        return linkTo(CityController.class).withRel(rel);
    }

    public Link linkToCities() {
        return linkToCities(IanaLinkRelations.SELF.value());
    }

    public Link linkToState(Long stateId, String rel) {
        return linkTo(methodOn(StateController.class).find(stateId)).withRel(rel);
    }

    public Link linkToState(Long stateId) {
        return linkToState(stateId, IanaLinkRelations.SELF.value());
    }

    public Link linkToStates(String rel) {
        return linkTo(StateController.class).withRel(rel);
    }

    public Link linkToStates() {
        return linkToStates(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class).find(restaurantId, productId)).withRel(rel);
    }

    public Link linkToProduct(Long restaurantId, Long productId) {
        return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchens(String rel) {
        return linkTo(KitchenController.class).withRel(rel);
    }

    public Link linkToKitchens() {
        return linkToKitchens(IanaLinkRelations.SELF.value());
    }
}
