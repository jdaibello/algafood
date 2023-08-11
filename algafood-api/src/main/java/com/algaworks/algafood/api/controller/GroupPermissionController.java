package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissionDTOAssembler;
import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.springdoc.controller.GroupPermissionControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.service.GroupService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PermissionDTO> fetchAll(@Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId) {
        Group group = service.findOrFail(groupId);

        CollectionModel<PermissionDTO> permissionsDTO = permissionDTOAssembler.toCollectionModel(group.getPermissions())
                .removeLinks();

        permissionsDTO.add(algaLinks.linkToGroupPermissions(groupId));

        if (algaSecurity.canEditUsersGroupsPermissions()) {
           permissionsDTO.add(algaLinks.linkToGroupPermissionAttachment(groupId, "attach"));

           permissionsDTO.getContent().forEach(permissionDTO -> {
               permissionDTO.add(algaLinks.linkToGroupPermissionDetachment(groupId, permissionDTO.getId(), "detach"));
           });
       }

        return permissionsDTO;
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @PutMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attach(@Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId,
                       @Parameter(description = "ID da permissão", example = "1") @PathVariable Long permissionId) {
        service.attachPermission(groupId, permissionId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detach(@Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId,
                                       @Parameter(description = "ID da permissão", example = "1") @PathVariable Long permissionId) {
        service.detachPermission(groupId, permissionId);

        return ResponseEntity.noContent().build();
    }
}
