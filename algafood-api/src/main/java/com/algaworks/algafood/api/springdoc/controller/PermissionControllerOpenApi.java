package com.algaworks.algafood.api.springdoc.controller;

import com.algaworks.algafood.api.dto.PermissionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Permissões")
public interface PermissionControllerOpenApi {

    @Operation(summary = "Lista as permissões")
    CollectionModel<PermissionDTO> fetchAll();
}
