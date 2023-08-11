package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.GroupDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "GroupsModel")
@Data
public class GroupsModelOpenApi {

    private GroupsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "GroupsEmbeddedModel")
    @Data
    public class GroupsEmbeddedModelOpenApi {

        private List<GroupDTO> groups;
    }
}
