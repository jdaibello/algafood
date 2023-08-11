package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantController;
import com.algaworks.algafood.api.dto.RestaurantOnlyNameDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantOnlyNameDTOAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantOnlyNameDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public RestaurantOnlyNameDTOAssembler() {
        super(RestaurantController.class, RestaurantOnlyNameDTO.class);
    }

    @Override
    public RestaurantOnlyNameDTO toModel(Restaurant restaurant) {
        RestaurantOnlyNameDTO restaurantDTO = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantDTO);

        if (algaSecurity.canQueryRestaurants()) {
            restaurantDTO.add(algaLinks.linkToRestaurants("restaurants"));
        }

        return restaurantDTO;
    }

    @Override
    public CollectionModel<RestaurantOnlyNameDTO> toCollectionModel(Iterable<? extends Restaurant> entities) {
        CollectionModel<RestaurantOnlyNameDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.canQueryRestaurants()) {
            collectionModel.add(algaLinks.linkToRestaurants());
        }

        return collectionModel;
    }
}
