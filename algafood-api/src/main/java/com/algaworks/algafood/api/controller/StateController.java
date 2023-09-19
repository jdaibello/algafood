package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.StateDTOAssembler;
import com.algaworks.algafood.api.assembler.StateInputDisassembler;
import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.api.dto.input.StateInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.springdoc.controller.StateControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.service.StateService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerOpenApi {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateService service;

    @Autowired
    private StateDTOAssembler stateDTOAssembler;

    @Autowired
    private StateInputDisassembler stateInputDisassembler;

    @Override
    @CheckSecurity.States.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<StateDTO> fetchAll() {
        return stateDTOAssembler.toCollectionModel(stateRepository.findAll());
    }

    @Override
    @CheckSecurity.States.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(value = "/{stateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StateDTO find(@Parameter(description = "ID do estado", example = "1") @PathVariable Long stateId) {
        State state = service.findOrFail(stateId);

        return stateDTOAssembler.toModel(state);
    }

    @Override
    @CheckSecurity.States.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public StateDTO add(@RequestBody @Valid StateInput stateInput) {
        State state = stateInputDisassembler.toDomainObject(stateInput);
        state = service.save(state);

        StateDTO stateDTO = stateDTOAssembler.toModel(state);
        ResourceUriHelper.addUriInResponseHeader(stateDTO.getId());

        return stateDTO;
    }

    @Override
    @CheckSecurity.States.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @PutMapping(value = "/{stateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StateDTO update(@Parameter(description = "ID do estado", example = "1") @PathVariable Long stateId,
                           @RequestBody @Valid StateInput stateInput) {
        State currentState = service.findOrFail(stateId);
        stateInputDisassembler.copyToDomainObject(stateInput, currentState);
        currentState = service.save(currentState);

        return stateDTOAssembler.toModel(currentState);
    }

    @Override
    @CheckSecurity.States.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "ID do estado", example = "1") @PathVariable Long stateId) {
        service.delete(stateId);
    }
}
