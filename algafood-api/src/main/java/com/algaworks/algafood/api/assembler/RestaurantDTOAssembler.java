package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.RestaurantDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestaurantDTO toModel(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public List<RestaurantDTO> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream().map(restaurant -> toModel(restaurant)).collect(Collectors.toList());
    }
}
