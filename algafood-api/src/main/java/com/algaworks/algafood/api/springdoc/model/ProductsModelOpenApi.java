package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.ProductDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "ProductsModel")
@Data
public class ProductsModelOpenApi {

    private ProductsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "ProductsEmbeddedModel")
    @Data
    public class ProductsEmbeddedModelOpenApi {

        private List<ProductDTO> products;
    }
}
