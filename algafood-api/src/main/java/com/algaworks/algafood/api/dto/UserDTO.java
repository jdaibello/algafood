package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users")
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Gustavo da Silva", required = true)
    private String name;

    @ApiModelProperty(example = "gustavosilva@gmail.com", required = true)
    private String email;
}
