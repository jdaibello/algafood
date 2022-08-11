package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

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
	public ResponseEntity<List<PaymentMethodDTO>> fetchAll(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

		String etag = "0";
		OffsetDateTime lastUpdateDate = paymentMethodRepository.getLastUpdateDate();

		if (lastUpdateDate != null) {
			etag = String.valueOf(lastUpdateDate.toEpochSecond());
		}

		if (request.checkNotModified(etag)) {
			return null;
		}

		List<PaymentMethodDTO> paymentMethodsDTO = paymentMethodDTOAssembler
				.toCollectionModel(paymentMethodRepository.findAll());

		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()).eTag(etag)
				.body(paymentMethodsDTO);
	}

	@GetMapping("/{paymentMethodId}")
	public ResponseEntity<PaymentMethodDTO> find(@PathVariable Long paymentMethodId) {
		PaymentMethod paymentMethod = service.findOrFail(paymentMethodId);
		PaymentMethodDTO paymentMethodDTO = paymentMethodDTOAssembler.toModel(paymentMethod);

		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.body(paymentMethodDTO);
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
