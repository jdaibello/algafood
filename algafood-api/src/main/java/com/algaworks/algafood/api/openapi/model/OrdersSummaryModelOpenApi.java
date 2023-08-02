package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("OrdersSummaryModel")
@Getter
@Setter
public class OrdersSummaryModelOpenApi {

    private OrdersSummaryEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @ApiModel("OrdersSummaryEmbeddedModel")
    @Data
    public class OrdersSummaryEmbeddedModelOpenApi {

        private List<OrderSummaryDTO> orders;
    }
}
