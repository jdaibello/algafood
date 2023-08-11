package com.algaworks.algafood.api.springdoc.model;

import com.algaworks.algafood.api.dto.PaymentMethodDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(name = "PaymentMethodsModel")
@Data
public class PaymentMethodsModelOpenApi {

    private PaymentMethodEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Schema(name = "PaymentMethodsEmbeddedModel")
    @Data
    public class PaymentMethodEmbeddedModelOpenApi {

        private List<PaymentMethodDTO> paymentMethods;
    }
}
