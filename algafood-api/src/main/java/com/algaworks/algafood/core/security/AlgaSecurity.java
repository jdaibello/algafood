package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            return false;
        }

        return restaurantRepository.existsResponsible(restaurantId, getUserId());
    }

    public boolean manageRestaurantOrder(String orderCode) {
        return orderRepository.isOrderManagedBy(orderCode, getUserId());
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean hasScopeRead() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean hasScopeWrite() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean canManageOrders(String orderCode) {
        return hasAuthority("SCOPE_WRITE") && (hasAuthority("GERENCIAR_PEDIDOS")
                || manageRestaurantOrder(orderCode));
    }

    public boolean canQueryRestaurants() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canManageRestaurantsRegistration() {
        return hasScopeWrite() && hasAuthority("EDITAR_RESTAURANTES");
    }

    public boolean canManageRestaurantsOperation(Long restaurantId) {
        return hasScopeWrite() && (hasAuthority("EDITAR_RESTAURANTES")
                || manageRestaurant(restaurantId));
    }

    public boolean canQueryUsersGroupsPermissions() {
        return hasScopeRead() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean canEditUsersGroupsPermissions() {
        return hasScopeWrite() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }

    public boolean canSearchOrders(Long restaurantId) {
        return hasScopeRead() && (hasAuthority("CONSULTAR_PEDIDOS")
                || manageRestaurant(restaurantId));
    }

    public boolean canSearchOrders() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canQueryPaymentMethods() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canQueryCities() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canQueryStates() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canQueryKitchens() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canQueryStatistics() {
        return hasScopeRead() && hasAuthority("GERAR_RELATORIOS");
    }
}
