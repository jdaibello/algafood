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

    public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
            new TemplateVariable("projection", TemplateVariable.VariableType.REQUEST_PARAM));

    public Link linkToOrders(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clientId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restaurantId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("startCreationDate", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("endCreationDate", TemplateVariable.VariableType.REQUEST_PARAM));

        String ordersUrl = linkTo(OrderController.class).toUri().toString();

        return Link.of(UriTemplate.of(ordersUrl, PAGE_VARIABLES.concat(filterVariables)), rel);
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

    public Link linkToRestaurantOpening(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class).open(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantClosing(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class).close(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantActivation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class).activate(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantDeactivation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class).inactivate(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurants(String rel) {
        String restaurantsUrl = linkTo(RestaurantController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantsUrl, PROJECTION_VARIABLES), rel);
    }

    public Link linkToRestaurants() {
        return linkToRestaurants(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPaymentMethods(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).fetchAll(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantPaymentMethods(Long restaurantId) {
        return linkToRestaurantPaymentMethods(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPaymentMethodAttachment(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).attach(restaurantId, null)).withRel(rel);
    }

    public Link linkToRestaurantPaymentMethodDetachment(Long restaurantId, Long paymentMethodId, String rel) {
        return linkTo(methodOn(RestaurantPaymentMethodController.class).detach(restaurantId, paymentMethodId)).withRel(rel);
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

    public Link linkToUserGroupAttachment(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class).attach(userId, null)).withRel(rel);
    }

    public Link linkToUserGroupDetachment(Long userId, Long groupId, String rel) {
        return linkTo(methodOn(UserGroupController.class).detach(userId, groupId)).withRel(rel);
    }

    public Link linkToRestaurantResponsible(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantResponsibleUserController.class).fetchAll(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantResponsible(Long restaurantId) {
        return linkToRestaurantResponsible(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantResponsibleAttachment(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantResponsibleUserController.class).attach(restaurantId, null)).withRel(rel);
    }

    public Link linkToRestaurantResponsibleDetachment(Long restaurantId, Long userId, String rel) {
        return linkTo(methodOn(RestaurantResponsibleUserController.class).detach(restaurantId, userId)).withRel(rel);
    }

    public Link linkToPaymentMethod(Long paymentMethodId, String rel) {
        return linkTo(methodOn(PaymentMethodController.class).find(paymentMethodId, null)).withRel(rel);
    }

    public Link linkToPaymentMethod(Long paymentMethodId) {
        return linkToPaymentMethod(paymentMethodId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPaymentMethods(String rel) {
        return linkTo(PaymentMethodController.class).withRel(rel);
    }

    public Link linkToPaymentMethods() {
        return linkToPaymentMethods(IanaLinkRelations.SELF.value());
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

    public Link linkToProducts(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class).fetchAll(restaurantId, null)).withRel(rel);
    }

    public Link linkToProducts(Long restaurantId) {
        return linkToProducts(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProductPhoto(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductPhotoController.class).find(restaurantId, productId)).withRel(rel);
    }

    public Link linkToProductPhoto(Long restaurantId, Long productId) {
        return linkToProductPhoto(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchen(Long kitchenId, String rel) {
        return linkTo(methodOn(KitchenController.class).find(kitchenId)).withRel(rel);
    }

    public Link linkToKitchen(Long kitchenId) {
        return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchens(String rel) {
        return linkTo(KitchenController.class).withRel(rel);
    }

    public Link linkToKitchens() {
        return linkToKitchens(IanaLinkRelations.SELF.value());
    }

    public Link linkToGroups(String rel) {
        return linkTo(GroupController.class).withRel(rel);
    }

    public Link linkToGroups() {
        return linkToGroups(IanaLinkRelations.SELF.value());
    }

    public Link linkToGroupPermissions(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).fetchAll(groupId)).withRel(rel);
    }

    public Link linkToGroupPermissions(Long groupId) {
        return linkToGroupPermissions(groupId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGroupPermissionAttachment(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).attach(groupId, null)).withRel(rel);
    }

    public Link linkToGroupPermissionDetachment(Long groupId, Long permissionId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class).detach(groupId, permissionId)).withRel(rel);
    }

    public Link linkToPermissions(String rel) {
        return linkTo(PermissionController.class).withRel(rel);
    }

    public Link linkToPermissions() {
        return linkToPermissions(IanaLinkRelations.SELF.value());
    }
}
