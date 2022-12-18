package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.assembler.GroupInputDisassembler;
import com.algaworks.algafood.api.controller.openapi.GroupControllerOpenApi;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.dto.input.GroupInput;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.repository.GroupRepository;
import com.algaworks.algafood.domain.service.GroupService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController implements GroupControllerOpenApi {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupService service;

	@Autowired
	private GroupDTOAssembler groupDTOAssembler;

	@Autowired
	private GroupInputDisassembler groupInputDisassembler;

	@Override
	@GetMapping
	public List<GroupDTO> fetchAll() {
		return groupDTOAssembler.toCollectionModel(groupRepository.findAll());
	}

	@Override
	@GetMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GroupDTO find(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		Group group = service.findOrFail(groupId);

		return groupDTOAssembler.toModel(group);
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public GroupDTO add(@RequestBody @Valid GroupInput groupInput) {
		Group group = groupInputDisassembler.toDomainObject(groupInput);
		group = service.save(group);

		return groupDTOAssembler.toModel(group);
	}

	@Override
	@PutMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GroupDTO update(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
			@RequestBody @Valid GroupInput groupInput) {
		Group currentGroup = service.findOrFail(groupId);
		groupInputDisassembler.copyToDomainObject(groupInput, currentGroup);
		currentGroup = service.save(currentGroup);

		return groupDTOAssembler.toModel(currentGroup);
	}

	@Override
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		service.delete(groupId);
	}
}
