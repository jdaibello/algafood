package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Setter
@Getter
public class CitySummaryDTO extends RepresentationModel<CitySummaryDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Fortaleza", required = true)
    private String name;

    @ApiModelProperty(example = "Ceará", required = true)
    private String state;
}
