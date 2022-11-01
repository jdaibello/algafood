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
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;

import io.swagger.annotations.Api;

@Api(tags = "Permissões dos Grupos")
@RestController
@RequestMapping(value = "/groups/{groupId}/permissions")
public class GroupPermissionController {

	@Autowired
	private GroupService service;

	@Autowired
	private PermissionDTOAssembler permissionDTOAssembler;

	@GetMapping
	public List<PermissionDTO> fetchAll(@PathVariable Long groupId) {
		Group group = service.findOrFail(groupId);

		return permissionDTOAssembler.toCollectionModel(group.getPermissions());
	}

	@PutMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@PathVariable Long groupId, @PathVariable Long permissionId) {
		service.attachPermission(groupId, permissionId);
	}

	@DeleteMapping("/{permissionId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@PathVariable Long groupId, @PathVariable Long permissionId) {
		service.detachPermission(groupId, permissionId);
	}
}
