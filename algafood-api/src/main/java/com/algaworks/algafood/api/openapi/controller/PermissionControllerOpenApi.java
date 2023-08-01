package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.PermissionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissionControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissionDTO> fetchAll();
}
