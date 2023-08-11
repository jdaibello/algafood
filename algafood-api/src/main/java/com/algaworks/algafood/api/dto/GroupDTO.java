package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "groups")
@Getter
@Setter
public class GroupDTO extends RepresentationModel<GroupDTO> {

    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "Garçons", required = true)
    private String name;
}
