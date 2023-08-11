package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.OrderSummaryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "OrdersSummaryModel")
@Getter
@Setter
public class OrdersSummaryModelOpenApi {

    private OrdersSummaryEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Schema(name = "OrdersSummaryEmbeddedModel")
    @Data
    public class OrdersSummaryEmbeddedModelOpenApi {

        private List<OrderSummaryDTO> orders;
    }
}
