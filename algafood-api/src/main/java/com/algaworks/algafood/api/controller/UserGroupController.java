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

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos de Usuários")
@RestController
@RequestMapping(value = "/users/{userId}/groups")
public class UserGroupController {

	@Autowired
	private UserService service;

	@Autowired
	private GroupDTOAssembler groupDTOAssembler;

	@ApiOperation("Listar")
	@GetMapping
	public List<GroupDTO> fetchAll(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		User user = service.findOrFail(userId);

		return groupDTOAssembler.toCollectionModel(user.getGroups());
	}

	@ApiOperation("Anexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Usuário e grupo anexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do usuário/grupo inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Usuário/grupo não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId,
			@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		service.attachGroup(userId, groupId);
	}

	@ApiOperation("Desanexar")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Usuário e grupo desanexados"),
			@ApiResponse(
				responseCode = "400",
				description = "ID do usuário/grupo inválido(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Usuário/grupo não encontrado(s)",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId,
			@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		service.detachGroup(userId, groupId);
	}
}
