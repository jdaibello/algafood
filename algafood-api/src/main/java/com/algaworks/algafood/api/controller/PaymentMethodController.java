package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.algaworks.algafood.api.assembler.PaymentMethodInputDisassembler;
import com.algaworks.algafood.api.dto.PaymentMethodDTO;
import com.algaworks.algafood.api.dto.input.PaymentMethodInput;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;
import com.algaworks.algafood.domain.service.PaymentMethodService;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Autowired
	private PaymentMethodService service;

	@Autowired
	private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

	@Autowired
	private PaymentMethodInputDisassembler paymentMethodInputDisassembler;

	@GetMapping
	public List<PaymentMethodDTO> fetchAll() {
		return paymentMethodDTOAssembler.toCollectionModel(paymentMethodRepository.findAll());
	}

	@GetMapping("/{paymentMethodId}")
	public PaymentMethodDTO find(@PathVariable Long paymentMethodId) {
		PaymentMethod paymentMethod = service.findOrFail(paymentMethodId);

		return paymentMethodDTOAssembler.toModel(paymentMethod);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentMethodDTO add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod paymentMethod = paymentMethodInputDisassembler.toDomainObject(paymentMethodInput);
		paymentMethod = service.save(paymentMethod);

		return paymentMethodDTOAssembler.toModel(paymentMethod);
	}

	@PutMapping("/{paymentMethodId}")
	public PaymentMethodDTO update(@PathVariable Long paymentMethodId,
			@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		PaymentMethod currentPaymentMethod = service.findOrFail(paymentMethodId);
		paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, currentPaymentMethod);
		currentPaymentMethod = service.save(currentPaymentMethod);

		return paymentMethodDTOAssembler.toModel(currentPaymentMethod);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long paymentMethodId) {
		service.delete(paymentMethodId);
	}
}
