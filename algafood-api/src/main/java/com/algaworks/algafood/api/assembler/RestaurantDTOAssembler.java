package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.dto.RestaurantDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantDTOAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestaurantDTOAssembler() {
        super(RestaurantController.class, RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO toModel(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantDTO);

        if (algaSecurity.canQueryRestaurants()) {
            restaurantDTO.add(algaLinks.linkToRestaurants("restaurants"));
            restaurantDTO.add(algaLinks.linkToProducts(restaurant.getId(), "products"));
            restaurantDTO.add(algaLinks.linkToRestaurantPaymentMethods(restaurant.getId(), "payment-methods"));
        }

        if (algaSecurity.canQueryKitchens()) {
            restaurantDTO.getKitchen().add(algaLinks.linkToKitchen(restaurant.getKitchen().getId()));
        }

        if (algaSecurity.canQueryCities()) {
            if (restaurantDTO.getAddress() != null && restaurantDTO.getAddress().getCity() != null) {
                restaurantDTO.getAddress().getCity().add(algaLinks.linkToCity(restaurant.getAddress().getCity().getId()));
            }
        }

        if (algaSecurity.canManageRestaurantsRegistration()) {
            restaurantDTO.add(algaLinks.linkToRestaurantResponsible(restaurant.getId(), "responsible"));

            if (restaurant.allowedToActivate()) {
                restaurantDTO.add(algaLinks.linkToRestaurantActivation(restaurant.getId(), "activate"));
            }

            if (restaurant.allowedToDeactivate()) {
                restaurantDTO.add(algaLinks.linkToRestaurantDeactivation(restaurant.getId(), "deactivate"));
            }
        }

        if (algaSecurity.canManageRestaurantsOperation(restaurant.getId())) {
            if (restaurant.allowedToOpen()) {
                restaurantDTO.add(algaLinks.linkToRestaurantOpening(restaurant.getId(), "open"));
            }

            if (restaurant.allowedToClose()) {
                restaurantDTO.add(algaLinks.linkToRestaurantClosing(restaurant.getId(), "close"));
            }
        }

        return restaurantDTO;
    }

    @Override
    public CollectionModel<RestaurantDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.canQueryRestaurants()) {
            collectionModel.add(algaLinks.linkToRestaurants());
        }

        return collectionModel;
    }
}
