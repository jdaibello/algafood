package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.KitchenDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "KitchensModel")
@Getter
@Setter
public class KitchensModelOpenApi {

    private KitchensEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Schema(name = "KitchensEmbeddedModel")
    @Data
    public class KitchensEmbeddedModelOpenApi {

        private List<KitchenDTO> kitchens;
    }
}
