package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.StateDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "StatesModel")
@Data
public class StatesModelOpenApi {

    private StatesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "StatesEmbeddedModel")
    @Data
    public class StatesEmbeddedModelOpenApi {

        private List<StateDTO> states;
    }
}
