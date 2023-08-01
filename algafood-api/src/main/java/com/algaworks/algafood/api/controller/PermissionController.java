package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissionDTOAssembler;
import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.openapi.controller.PermissionControllerOpenApi;
import com.algaworks.algafood.domain.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionDTOAssembler permissionDTOAssembler;

    @Override
    @GetMapping
    public CollectionModel<PermissionDTO> fetchAll() {
        return permissionDTOAssembler.toCollectionModel(permissionRepository.findAll());
    }
}
