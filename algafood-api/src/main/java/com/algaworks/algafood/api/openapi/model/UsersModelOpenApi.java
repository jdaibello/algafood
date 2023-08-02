package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.UserDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsersModel")
@Data
public class UsersModelOpenApi {

    private UsersEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("UsersEmbeddedModel")
    @Data
    public class UsersEmbeddedModelOpenApi {

        private List<UserDTO> users;
    }
}
