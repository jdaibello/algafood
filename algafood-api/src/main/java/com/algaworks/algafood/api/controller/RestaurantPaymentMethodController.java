package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.springdoc.controller.RestaurantPaymentMethodControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<PaymentMethodDTO> fetchAll(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        Restaurant restaurant = service.findOrFail(restaurantId);

        CollectionModel<PaymentMethodDTO> paymentMethodsDTO = paymentMethodDTOAssembler
                .toCollectionModel(restaurant.getPaymentMethods()).removeLinks();

        paymentMethodsDTO.add(algaLinks.linkToRestaurantPaymentMethods(restaurantId));

        if (algaSecurity.canManageRestaurantsOperation(restaurantId)) {
            paymentMethodsDTO.add(algaLinks.linkToRestaurantPaymentMethodAttachment(restaurantId, "attach"));

            paymentMethodsDTO.getContent().forEach(paymentMethodDTO -> {
                paymentMethodDTO.add(algaLinks.linkToRestaurantPaymentMethodDetachment(restaurantId,
                        paymentMethodDTO.getId(), "detach"));
            });
        }

        return  paymentMethodsDTO;
    }

    @Override
    @CheckSecurity.Restaurants.CanManageOperation
    @SecurityRequirement(name = "OAuth2")
    @PutMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attach(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @Parameter(description = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
        service.attachPaymentMethod(restaurantId, paymentMethodId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageOperation
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping("/{paymentMethodId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detach(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                                 @Parameter(description = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
        service.detachPaymentMethod(restaurantId, paymentMethodId);

        return ResponseEntity.noContent().build();
    }
}
