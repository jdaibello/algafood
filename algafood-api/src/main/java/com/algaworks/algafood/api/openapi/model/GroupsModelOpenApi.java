package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.GroupDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("GroupsModel")
@Data
public class GroupsModelOpenApi {

    private GroupsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("GroupsEmbeddedModel")
    @Data
    public class GroupsEmbeddedModelOpenApi {

        private List<GroupDTO> groups;
    }
}
