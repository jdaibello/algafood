package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.PermissionDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "PermissionsModel")
@Data
public class PermissionsModelOpenApi {

    private PermissionsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "PermissionsEmbeddedModel")
    @Data
    public class PermissionsEmbeddedModelOpenApi {

        private List<PermissionDTO> permissions;
    }
}
