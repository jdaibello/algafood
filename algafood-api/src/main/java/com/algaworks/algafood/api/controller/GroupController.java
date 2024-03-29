package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.assembler.GroupInputDisassembler;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.dto.input.GroupInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.springdoc.controller.GroupControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Group;
import com.algaworks.algafood.domain.repository.GroupRepository;
import com.algaworks.algafood.domain.service.GroupService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController implements GroupControllerOpenApi {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService service;

    @Autowired
    private GroupDTOAssembler groupDTOAssembler;

    @Autowired
    private GroupInputDisassembler groupInputDisassembler;

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping
    public CollectionModel<GroupDTO> fetchAll() {
        return groupDTOAssembler.toCollectionModel(groupRepository.findAll());
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDTO find(@Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId) {
        Group group = service.findOrFail(groupId);

        return groupDTOAssembler.toModel(group);
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO add(@RequestBody @Valid GroupInput groupInput) {
        Group group = groupInputDisassembler.toDomainObject(groupInput);
        group = service.save(group);

        GroupDTO groupDTO = groupDTOAssembler.toModel(group);
        ResourceUriHelper.addUriInResponseHeader(groupDTO.getId());

        return groupDTO;
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @PutMapping(value = "/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GroupDTO update(@Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId,
                           @RequestBody @Valid GroupInput groupInput) {
        Group currentGroup = service.findOrFail(groupId);
        groupInputDisassembler.copyToDomainObject(groupInput, currentGroup);
        currentGroup = service.save(currentGroup);

        return groupDTOAssembler.toModel(currentGroup);
    }

    @Override
    @CheckSecurity.UsersGroupsPermissions.CanEdit
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "ID do grupo", example = "1") @PathVariable Long groupId) {
        service.delete(groupId);
    }
}
