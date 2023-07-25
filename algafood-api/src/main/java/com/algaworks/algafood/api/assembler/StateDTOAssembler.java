package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public StateDTO toModel(State state) {
        return modelMapper.map(state, StateDTO.class);
    }

    public List<StateDTO> toCollectionModel(List<State> states) {
        return states.stream().map(state -> toModel(state)).collect(Collectors.toList());
    }
}
