package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.UserDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "UsersModel")
@Data
public class UsersModelOpenApi {

    private UsersEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "UsersEmbeddedModel")
    @Data
    public class UsersEmbeddedModelOpenApi {

        private List<UserDTO> users;
    }
}
