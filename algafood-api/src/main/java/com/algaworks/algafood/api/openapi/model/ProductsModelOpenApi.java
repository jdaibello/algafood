package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.ProductDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProductsModel")
@Data
public class ProductsModelOpenApi {

    private ProductsEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("ProductsEmbeddedModel")
    @Data
    public class ProductsEmbeddedModelOpenApi {

        private List<ProductDTO> products;
    }
}
