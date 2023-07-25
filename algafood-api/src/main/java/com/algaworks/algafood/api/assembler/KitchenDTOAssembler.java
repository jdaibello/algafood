package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KitchenDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public KitchenDTO toModel(Kitchen kitchen) {
        return modelMapper.map(kitchen, KitchenDTO.class);
    }

    public List<KitchenDTO> toCollectionModel(List<Kitchen> kitchens) {
        return kitchens.stream().map(kitchen -> toModel(kitchen)).collect(Collectors.toList());
    }
}
