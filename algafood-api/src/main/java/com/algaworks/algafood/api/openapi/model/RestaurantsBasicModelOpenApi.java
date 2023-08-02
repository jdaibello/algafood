package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.RestaurantBasicDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantsBasicModel")
@Data
public class RestaurantsBasicModelOpenApi {

    private RestaurantsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("RestaurantsEmbeddedModel")
    @Data
    public class RestaurantsEmbeddedModelOpenApi {

        private List<RestaurantBasicDTO> restaurants;
    }
}
