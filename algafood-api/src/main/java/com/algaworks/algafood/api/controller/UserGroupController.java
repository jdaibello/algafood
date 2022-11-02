package com.algaworks.algafood.api.controller;

import java.util.List;

import io.swagger.annotations.ApiParam;
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
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId, @ApiParam(value =
			"ID do grupo", example = "1") @PathVariable Long groupId) {
		service.attachGroup(userId, groupId);
	}

	@ApiOperation("Desanexar")
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId, @ApiParam(value =
			"ID do grupo", example = "1") @PathVariable Long groupId) {
		service.detachGroup(userId, groupId);
	}
}
