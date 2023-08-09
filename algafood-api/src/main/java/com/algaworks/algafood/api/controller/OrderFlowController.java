package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.OrderFlowControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.service.OrderFlowService;
import io.swagger.annotations.ApiParam;
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
    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> confirm(@ApiParam(
            value = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.confirm(orderCode);

        return  ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> cancel(@ApiParam(
            value = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.cancel(orderCode);

        return  ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Orders.CanManageOrders
    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delivery(@ApiParam(
            value = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.delivery(orderCode);

        return  ResponseEntity.noContent().build();
    }
}
