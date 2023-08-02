package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.StateDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("StatesModel")
@Data
public class StatesModelOpenApi {

    private StatesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("StatesEmbeddedModel")
    @Data
    public class StatesEmbeddedModelOpenApi {

        private List<StateDTO> states;
    }
}
