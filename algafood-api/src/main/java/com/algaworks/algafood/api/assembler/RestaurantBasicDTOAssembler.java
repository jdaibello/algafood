package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.dto.RestaurantBasicDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
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

    public RestaurantBasicDTOAssembler() {
        super(RestaurantController.class, RestaurantBasicDTO.class);
    }

    @Override
    public RestaurantBasicDTO toModel(Restaurant restaurant) {
        RestaurantBasicDTO restaurantDTO = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantDTO);

        restaurantDTO.add(algaLinks.linkToRestaurants("restaurants"));
        restaurantDTO.getKitchen().add(algaLinks.linkToKitchen(restaurant.getKitchen().getId()));

        return restaurantDTO;
    }

    @Override
    public CollectionModel<RestaurantBasicDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurants());
    }
}
