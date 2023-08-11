package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "kitchens")
@Setter
@Getter
public class KitchenDTO extends RepresentationModel<KitchenDTO> {

    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "Brasileira", required = true)
    private String name;
}
