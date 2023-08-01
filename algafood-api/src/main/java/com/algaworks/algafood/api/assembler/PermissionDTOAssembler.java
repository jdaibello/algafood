package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.PermissionDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PermissionDTOAssembler implements RepresentationModelAssembler<Permission, PermissionDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    public PermissionDTO toModel(Permission permission) {
        return modelMapper.map(permission, PermissionDTO.class);
    }

    @Override
    public CollectionModel<PermissionDTO> toCollectionModel(Iterable<? extends Permission> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissions());
    }
}
