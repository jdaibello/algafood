package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.StateDTOAssembler;
import com.algaworks.algafood.api.assembler.StateInputDisassembler;
import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.api.dto.input.StateInput;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.StateRepository;
import com.algaworks.algafood.domain.service.StateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estados")
@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private StateService service;

	@Autowired
	private StateDTOAssembler stateDTOAssembler;

	@Autowired
	private StateInputDisassembler stateInputDisassembler;

	@ApiOperation("Listar")
	@GetMapping
	public List<StateDTO> fetchAll() {
		return stateDTOAssembler.toCollectionModel(stateRepository.findAll());
	}

	@ApiOperation("Buscar por ID")
	@GetMapping("/{stateId}")
	public StateDTO find(@PathVariable Long stateId) {
		State state = service.findOrFail(stateId);

		return stateDTOAssembler.toModel(state);
	}

	@ApiOperation("Adicionar")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StateDTO add(@RequestBody @Valid StateInput stateInput) {
		State state = stateInputDisassembler.toDomainObject(stateInput);
		state = service.save(state);

		return stateDTOAssembler.toModel(state);
	}

	@ApiOperation("Atualizar")
	@PutMapping("/{stateId}")
	public StateDTO update(@PathVariable Long stateId, @RequestBody @Valid StateInput stateInput) {
		State currentState = service.findOrFail(stateId);
		stateInputDisassembler.copyToDomainObject(stateInput, currentState);
		currentState = service.save(currentState);

		return stateDTOAssembler.toModel(currentState);
	}

	@ApiOperation("Excluir")
	@DeleteMapping("/{stateId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long stateId) {
		service.delete(stateId);
	}
}
