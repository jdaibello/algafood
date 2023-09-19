package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.OrderDTOAssembler;
import com.algaworks.algafood.api.assembler.OrderInputDisassembler;
import com.algaworks.algafood.api.assembler.OrderSummaryDTOAssembler;
import com.algaworks.algafood.api.dto.OrderDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.input.OrderInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.springdoc.controller.OrderControllerOpenApi;
import com.algaworks.algafood.core.data.PageWrapper;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.filter.OrderFilter;
import com.algaworks.algafood.domain.model.Order;
import com.algaworks.algafood.domain.model.User;
import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.service.OrderIssuanceService;
import com.algaworks.algafood.infrastructure.repository.spec.OrderSpecs;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController implements OrderControllerOpenApi {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderIssuanceService service;

    @Autowired
    private OrderDTOAssembler orderDTOAssembler;

    @Autowired
    private OrderSummaryDTOAssembler orderSummaryDTOAssembler;

    @Autowired
    private OrderInputDisassembler orderInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Order> pagedResourcesAssembler;

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @CheckSecurity.Orders.CanSearch
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<OrderSummaryDTO> search(OrderFilter filter, @PageableDefault(size = 10) Pageable pageable) {
        Pageable translatedPageable = translatePageable(pageable);

        Page<Order> ordersPage = orderRepository.findAll(OrderSpecs.usingFilter(filter), translatedPageable);
        ordersPage = new PageWrapper<>(ordersPage, pageable);

        return pagedResourcesAssembler.toModel(ordersPage, orderSummaryDTOAssembler);
    }

    @Override
    @CheckSecurity.Orders.CanFind
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(value = "/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDTO find(@Parameter(
            description = "Código UUID do pedido",
            example = "123e4567-e89b-12d3-a456-426655440000") @PathVariable String orderCode) {
        Order order = service.findOrFail(orderCode);

        return orderDTOAssembler.toModel(order);
    }

    @Override
    @CheckSecurity.Orders.CanCreate
    @SecurityRequirement(name = "OAuth2")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO add(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = orderInputDisassembler.toDomainObject(orderInput);

            newOrder.setClient(new User());
            newOrder.getClient().setId(algaSecurity.getUserId());

            newOrder = service.issue(newOrder);

            OrderDTO orderDTO = orderDTOAssembler.toModel(newOrder);
            ResourceUriHelper.addUriInResponseHeader(orderDTO.getCode());

            return orderDTO;
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    private Pageable translatePageable(Pageable apiPageable) {
        var mapper = Map.of("code", "code", "subtotal", "subtotal", "shippingFee", "shippingFee", "creationDate",
                "creationdate", "restaurant.id", "restaurant.id", "restaurant.name", "restaurant.name", "client.id",
                "client.id", "client.name", "client.name");

        return PageableTranslator.translate(apiPageable, mapper);
    }
}
