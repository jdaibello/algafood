package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.input.KitchenInput;
import com.algaworks.algafood.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Kitchen toDomainObject(KitchenInput kitchenInput) {
        return modelMapper.map(kitchenInput, Kitchen.class);
    }

    public void copyToDomainObject(KitchenInput kitchenInput, Kitchen kitchen) {
        modelMapper.map(kitchenInput, kitchen);
    }
}
