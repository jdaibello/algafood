package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("FormasDePagamentoModel")
@Data
public class PaymentMethodsModelOpenApi {

    private PaymentMethodEmbeddedModelOpenApi _embedded;
    private Links _links;

    @ApiModel("FormasDePagamentoEmbeddedModel")
    @Data
    public class PaymentMethodEmbeddedModelOpenApi {

        private List<PaymentMethodDTO> paymentMethods;
    }
}
