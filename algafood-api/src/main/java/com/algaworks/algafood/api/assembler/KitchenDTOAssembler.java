package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.KitchenController;
import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class KitchenDTOAssembler extends RepresentationModelAssemblerSupport<Kitchen, KitchenDTO> {

    @Autowired
    private ModelMapper modelMapper;

    public KitchenDTOAssembler() {
        super(KitchenController.class, KitchenDTO.class);
    }

    @Override
    public KitchenDTO toModel(Kitchen kitchen) {
        KitchenDTO kitchenDTO = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenDTO);

        kitchenDTO.add(linkTo(KitchenController.class).withRel("kitchens"));

        return kitchenDTO;
    }
}
