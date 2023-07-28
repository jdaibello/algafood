package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.StateController;
import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class StateDTOAssembler extends RepresentationModelAssemblerSupport<State, StateDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public StateDTOAssembler() {
        super(StateController.class, StateDTO.class);
    }

    public StateDTO toModel(State state) {
        StateDTO stateDTO = createModelWithId(state.getId(), state);
        modelMapper.map(state, stateDTO);

        stateDTO.add(algaLinks.linkToStates("states"));

        return stateDTO;
    }

    @Override
    public CollectionModel<StateDTO> toCollectionModel(Iterable<? extends State> entities) {
        return super.toCollectionModel(entities).add(linkTo(StateController.class).withSelfRel());
    }
}
