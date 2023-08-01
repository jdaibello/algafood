package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissionDTOAssembler;
import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.openapi.controller.GroupPermissionControllerOpenApi;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/groups/{groupId}/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupPermissionController implements GroupPermissionControllerOpenApi {

    @Autowired
    private GroupService service;

    @Autowired
    private PermissionDTOAssembler permissionDTOAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PermissionDTO> fetchAll(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId) {
        Group group = service.findOrFail(groupId);
        CollectionModel<PermissionDTO> permissionsDTO = permissionDTOAssembler.toCollectionModel(group.getPermissions())
                .removeLinks().add(algaLinks.linkToGroupPermissions(groupId))
                .add(algaLinks.linkToGroupPermissionAttachment(groupId, "attach"));

        permissionsDTO.getContent().forEach(permissionDTO -> {
            permissionDTO.add(algaLinks.linkToGroupPermissionDetachment(groupId, permissionDTO.getId(), "detach"));
        });

        return permissionsDTO;
    }

    @Override
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attach(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
                       @ApiParam(value = "ID da permissão", example = "1") @PathVariable Long permissionId) {
        service.attachPermission(groupId, permissionId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detach(@ApiParam(value = "ID do grupo", example = "1") @PathVariable Long groupId,
                                       @ApiParam(value = "ID da permissão", example = "1") @PathVariable Long permissionId) {
        service.detachPermission(groupId, permissionId);

        return ResponseEntity.noContent().build();
    }
}
