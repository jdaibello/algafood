package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "users")
@Getter
@Setter
public class UserDTO extends RepresentationModel<UserDTO> {

    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "Gustavo da Silva", required = true)
    private String name;

    @Schema(example = "gustavosilva@gmail.com", required = true)
    private String email;
}
