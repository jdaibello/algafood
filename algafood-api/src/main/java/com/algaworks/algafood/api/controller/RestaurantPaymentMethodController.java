package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.openapi.controller.RestaurantPaymentMethodControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController implements RestaurantPaymentMethodControllerOpenApi {

    @Autowired
    private RestaurantService service;

    @Autowired
    private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PaymentMethodDTO> fetchAll(
            @ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        Restaurant restaurant = service.findOrFail(restaurantId);

        return paymentMethodDTOAssembler.toCollectionModel(restaurant.getPaymentMethods()).removeLinks()
                .add(algaLinks.linkToRestaurantPaymentMethods(restaurantId));
    }

    @Override
    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
        service.attachPaymentMethod(restaurantId, paymentMethodId);
    }

    @Override
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
        service.detachPaymentMethod(restaurantId, paymentMethodId);
    }
}
