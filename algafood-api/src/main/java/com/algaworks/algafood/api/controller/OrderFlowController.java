package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.springdoc.controller.OrderFlowControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.OrderFlowService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderFlowController implements OrderFlowControllerOpenApi {

    @Autowired
    private OrderFlowService orderFlowService;

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @SecurityRequirement(name = "OAuth2")
    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@Parameter(
            description = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.confirm(orderCode);

        return  ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @SecurityRequirement(name = "OAuth2")
    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@Parameter(
            description = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.cancel(orderCode);

        return  ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @SecurityRequirement(name = "OAuth2")
    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delivery(@Parameter(
            description = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.delivery(orderCode);

        return  ResponseEntity.noContent().build();
    }
}
