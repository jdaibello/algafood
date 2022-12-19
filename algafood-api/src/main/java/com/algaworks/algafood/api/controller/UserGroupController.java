package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.openapi.controller.UserGroupControllerOpenApi;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {

	@Autowired
	private UserService service;

	@Autowired
	private GroupDTOAssembler groupDTOAssembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GroupDTO> fetchAll(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
		User user = service.findOrFail(userId);

		return groupDTOAssembler.toCollectionModel(user.getGroups());
	}

	@Override
	@PutMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId,
			@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		service.attachGroup(userId, groupId);
	}

	@Override
	@DeleteMapping("/{groupId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId,
			@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
		service.detachGroup(userId, groupId);
	}
}
