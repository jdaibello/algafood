package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.PermissionDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissionsModel")
@Data
public class PermissionsModelOpenApi {

    private PermissionsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("PermissionsEmbeddedModel")
    @Data
    public class PermissionsEmbeddedModelOpenApi {

        private List<PermissionDTO> permissions;
    }
}
