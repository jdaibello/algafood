package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.dto.RestaurantBasicDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantBasicDTOAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantBasicDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestaurantBasicDTOAssembler() {
        super(RestaurantController.class, RestaurantBasicDTO.class);
    }

    @Override
    public RestaurantBasicDTO toModel(Restaurant restaurant) {
        RestaurantBasicDTO restaurantDTO = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantDTO);

        if (algaSecurity.canQueryRestaurants()) {
            restaurantDTO.add(algaLinks.linkToRestaurants("restaurants"));
        }

        if (algaSecurity.canQueryKitchens()) {
            restaurantDTO.getKitchen().add(algaLinks.linkToKitchen(restaurant.getKitchen().getId()));
        }

        return restaurantDTO;
    }

    @Override
    public CollectionModel<RestaurantBasicDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantBasicDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.canQueryRestaurants()) {
            collectionModel.add(algaLinks.linkToRestaurants());
        }

        return collectionModel;
    }
}
