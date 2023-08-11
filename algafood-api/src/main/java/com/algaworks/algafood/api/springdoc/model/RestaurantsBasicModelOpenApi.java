package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.RestaurantBasicDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "RestaurantsBasicModel")
@Data
public class RestaurantsBasicModelOpenApi {

    private RestaurantsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "RestaurantsEmbeddedModel")
    @Data
    public class RestaurantsEmbeddedModelOpenApi {

        private List<RestaurantBasicDTO> restaurants;
    }
}
