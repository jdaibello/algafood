package com.algaworks.algafood.api.controller;

import java.util.List;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Formas de Pagamento dos Restaurantes")
@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodController {

	@Autowired
	private RestaurantService service;

	@Autowired
	private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

	@ApiOperation("Listar")
	@GetMapping
	public List<PaymentMethodDTO> fetchAll(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
		Restaurant restaurant = service.findOrFail(restaurantId);

		return paymentMethodDTOAssembler.toCollectionModel(restaurant.getPaymentMethods());
	}

	@ApiOperation("Anexar")
	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void attach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
					   @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
		service.attachPaymentMethod(restaurantId, paymentMethodId);
	}

	@ApiOperation("Desanexar")
	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void detach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
					   @ApiParam(value = "ID da forma de pagamento", example = "1") @PathVariable Long paymentMethodId) {
		service.detachPaymentMethod(restaurantId, paymentMethodId);
	}
}
