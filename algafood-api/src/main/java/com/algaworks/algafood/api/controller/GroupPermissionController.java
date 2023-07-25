package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissionDTOAssembler;
import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.openapi.controller.GroupPermissionControllerOpenApi;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

    @Autowired
    private GroupService service;

    @Autowired
    private PermissionDTOAssembler permissionDTOAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PermissionDTO> fetchAll(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
        Group group = service.findOrFail(groupId);

        return permissionDTOAssembler.toCollectionModel(group.getPermissions());
    }

    @Override
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
                       @ApiParam(value = "ID da permissão", example = "1") @PathVariable Long permissionId) {
        service.attachPermission(groupId, permissionId);
    }

    @Override
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
                       @ApiParam(value = "ID da permissão", example = "1") @PathVariable Long permissionId) {
        service.detachPermission(groupId, permissionId);
    }
}
