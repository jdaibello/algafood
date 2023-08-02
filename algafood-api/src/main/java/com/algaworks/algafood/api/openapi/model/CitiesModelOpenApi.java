package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.CityDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CitiesModel")
@Data
public class CitiesModelOpenApi {

    private CityEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("CitiesEmbeddedModel")
    @Data
    public class CityEmbeddedModelOpenApi {

        private List<CityDTO> cities;
    }
}
