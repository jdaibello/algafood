package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Kitchens {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanQuery { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanEdit { }
    }

    public @interface Restaurants {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanQuery { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanManageRegistration { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_RESTAURANTES') or " +
                "@algaSecurity.manageRestaurant(#restaurantId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanManageOperation { }
    }

    public @interface Orders {

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.getUserId() == #filter.clientId or" +
                "@algaSecurity.manageRestaurant(#filter.restaurantId))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanSearch { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.getUserId() == returnObject.client.id or " +
                "@algaSecurity.manageRestaurant(returnObject.restaurant.id)")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanFind { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanCreate { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or " +
                "@algaSecurity.manageRestaurantOrder(#orderCode))")
        @Retention(RetentionPolicy.RUNTIME)
        @Target(ElementType.METHOD)
        public @interface CanManageOrders { }
    }
}
