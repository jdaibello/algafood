package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissionDTOAssembler;
import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Permissões dos Grupos")
@RestController
@RequestMapping(value = "/groups/{groupId}/permissions")
public class GroupPermissionController {

	@Autowired
	private GroupService service;

	@Autowired
	private PermissionDTOAssembler permissionDTOAssembler;

	@ApiOperation("Listar")
	@GetMapping
	public List<PermissionDTO> fetchAll(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		Group group = service.findOrFail(groupId);

		return permissionDTOAssembler.toCollectionModel(group.getPermissions());
	}

	@ApiOperation("Anexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Grupo e permissão anexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do grupo/permissão inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Grupo/permissão não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
			@ApiParam(value = "ID da permissão", example = "1") @PathVariable Long permissionId) {
		service.attachPermission(groupId, permissionId);
	}

	@ApiOperation("Desanexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Grupo e permissão desanexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do grupo/permissão inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Grupo/permissão não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
			@ApiParam(value = "ID da permissão", example = "1") @PathVariable Long permissionId) {
		service.detachPermission(groupId, permissionId);
	}
}
