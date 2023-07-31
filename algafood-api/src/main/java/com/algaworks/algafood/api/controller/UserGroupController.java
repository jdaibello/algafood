package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GroupDTOAssembler;
import com.algaworks.algafood.api.dto.GroupDTO;
import com.algaworks.algafood.api.openapi.controller.UserGroupControllerOpenApi;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.service.UserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/{userId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserGroupController implements UserGroupControllerOpenApi {

    @Autowired
    private UserService service;

    @Autowired
    private GroupDTOAssembler groupDTOAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<GroupDTO> fetchAll(@ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
        User user = service.findOrFail(userId);

        return groupDTOAssembler.toCollectionModel(user.getGroups()).removeLinks();
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
