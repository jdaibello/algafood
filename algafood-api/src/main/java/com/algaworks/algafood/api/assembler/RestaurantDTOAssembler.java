package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.dto.RestaurantDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
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

    public RestaurantDTOAssembler() {
        super(RestaurantController.class, RestaurantDTO.class);
    }

    @Override
    public RestaurantDTO toModel(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantDTO);

        restaurantDTO.add(algaLinks.linkToRestaurants("restaurants"));
        restaurantDTO.getKitchen().add(algaLinks.linkToKitchen(restaurant.getKitchen().getId()));
        restaurantDTO.getAddress().getCity().add(algaLinks.linkToCity(restaurant.getAddress().getCity().getId()));
        restaurantDTO.add(algaLinks.linkToRestaurantPaymentMethods(restaurant.getId(), "payment-methods"));
        restaurantDTO.add(algaLinks.linkToRestaurantResponsible(restaurant.getId(), "responsible"));

        return restaurantDTO;
    }

    @Override
    public CollectionModel<RestaurantDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities).add(algaLinks.linkToRestaurants());
    }
}
