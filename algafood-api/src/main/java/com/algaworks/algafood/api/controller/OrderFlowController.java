package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.OrderFlowControllerOpenApi;
import com.algaworks.algafood.domain.service.OrderFlowService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderFlowController implements OrderFlowControllerOpenApi {

    @Autowired
    private OrderFlowService orderFlowService;

    @Override
    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirm(@ApiParam(
            value = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.confirm(orderCode);
    }

    @Override
    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@ApiParam(
            value = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.cancel(orderCode);
    }

    @Override
    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delivery(@ApiParam(
            value = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        orderFlowService.delivery(orderCode);
    }
}
