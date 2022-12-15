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

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.assembler.GroupInputDisassembler;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.dto.input.GroupInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.repository.GroupRepository;
import com.algaworks.algafood.domain.service.GroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
@RestController
@RequestMapping("/groups")
public class GroupController {

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupService service;

	@Autowired
	private GroupDTOAssembler groupDTOAssembler;

	@Autowired
	private GroupInputDisassembler groupInputDisassembler;

	@ApiOperation("Listar")
	@GetMapping
	public List<GroupDTO> fetchAll() {
		return groupDTOAssembler.toCollectionModel(groupRepository.findAll());
	}

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID do grupo inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Grupo não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@GetMapping("/{groupId}")
	public GroupDTO find(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		Group group = service.findOrFail(groupId);

		return groupDTOAssembler.toModel(group);
	}

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Grupo cadastrado") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GroupDTO add(@RequestBody @Valid GroupInput groupInput) {
		Group group = groupInputDisassembler.toDomainObject(groupInput);
		group = service.save(group);

		return groupDTOAssembler.toModel(group);
	}

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Grupo atualizado"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do grupo inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Grupo não encontrado",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{groupId}")
	public GroupDTO update(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
			@RequestBody @Valid GroupInput groupInput) {
		Group currentGroup = service.findOrFail(groupId);
		groupInputDisassembler.copyToDomainObject(groupInput, currentGroup);
		currentGroup = service.save(currentGroup);

		return groupDTOAssembler.toModel(currentGroup);
	}

	@ApiOperation("Excluir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Grupo excluído"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do grupo inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Grupo não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		service.delete(groupId);
	}
}
