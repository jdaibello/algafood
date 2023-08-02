package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.KitchenDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("KitchensModel")
@Getter
@Setter
public class KitchensModelOpenApi {

    private KitchensEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("KitchensEmbeddedModel")
    @Data
    public class KitchensEmbeddedModelOpenApi {

        private List<KitchenDTO> kitchens;
    }
}
