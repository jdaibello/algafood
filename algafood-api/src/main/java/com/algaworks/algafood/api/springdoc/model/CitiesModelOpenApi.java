package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.CityDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "CitiesModel")
@Data
public class CitiesModelOpenApi {

    private CityEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "CitiesEmbeddedModel")
    @Data
    public class CityEmbeddedModelOpenApi {

        private List<CityDTO> cities;
    }
}
