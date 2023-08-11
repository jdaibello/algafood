package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CityController;
import com.algaworks.algafood.api.dto.CityDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CityDTOAssembler extends RepresentationModelAssemblerSupport<City, CityDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public CityDTOAssembler() {
        super(CityController.class, CityDTO.class);
    }

    @Override
    public CityDTO toModel(City city) {
        CityDTO cityDTO = createModelWithId(city.getId(), city);
        modelMapper.map(city, cityDTO);

        if (algaSecurity.canQueryCities()) {
            cityDTO.add(algaLinks.linkToCities("cities"));
        }

        if (algaSecurity.canQueryStates()) {
            cityDTO.getState().add(algaLinks.linkToState(cityDTO.getState().getId()));
        }

        return cityDTO;
    }

    @Override
    public CollectionModel<CityDTO> toCollectionModel(Iterable<? extends City> entities) {
        CollectionModel<CityDTO> collectionModel = super.toCollectionModel(entities);

        if (algaSecurity.canQueryCities()) {
            collectionModel.add(algaLinks.linkToCities());
        }

        return collectionModel;
    }
}
