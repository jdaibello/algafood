package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissions")
@Getter
@Setter
public class PermissionDTO extends RepresentationModel<PermissionDTO> {

    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "ADICIONAR_RESTAURANTES", required = true)
    private String name;

    @Schema(example = "Permite adicionar restaurantes", required = true)
    private String description;
}
