package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "states")
@Setter
@Getter
public class StateDTO extends RepresentationModel<StateDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "São Paulo", required = true)
    private String name;
}
