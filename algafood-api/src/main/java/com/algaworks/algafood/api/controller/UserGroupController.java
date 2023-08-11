package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.springdoc.controller.UserGroupControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {

    @Autowired
    private UserService service;

    @Autowired
    private GroupDTOAssembler groupDTOAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GroupDTO> fetchAll(@Parameter(description = "ID do usuário", example = "1") @PathVariable Long userId) {
        User user = service.findOrFail(userId);

        CollectionModel<GroupDTO> groupsDTO = groupDTOAssembler.toCollectionModel(user.getGroups()).removeLinks();

        if (algaSecurity.canEditUsersGroupsPermissions()) {
            groupsDTO.add(algaLinks.linkToUserGroups(userId));
            groupsDTO.add(algaLinks.linkToUserGroupAttachment(userId, "attach"));

            groupsDTO.getContent().forEach(groupDTO -> {
                groupDTO.add(algaLinks.linkToUserGroupDetachment(userId, groupDTO.getId(), "detach"));
            });
        }

        return groupsDTO;
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attach(@Parameter(description = "ID do usuário", example = "1") @PathVariable Long userId,
                                 @Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId) {
        service.attachGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detach(@Parameter(description = "ID do usuário", example = "1") @PathVariable Long userId,
                       @Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId) {
        service.detachGroup(userId, groupId);

        return ResponseEntity.noContent().build();
    }
}
