package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.PaymentMethodNotFoundException;
import com.algaworks.algafood.domain.model.PaymentMethod;
import com.algaworks.algafood.domain.repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {
	private static final String PAYMENT_METHOD_IN_USE_MSG = "Forma de pagamento de código %d não pode ser removida, pois está em uso";

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Transactional
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);
	}

	@Transactional
	public void delete(Long paymentMethodId) {
		try {
			paymentMethodRepository.deleteById(paymentMethodId);
			paymentMethodRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new PaymentMethodNotFoundException(paymentMethodId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(PAYMENT_METHOD_IN_USE_MSG, paymentMethodId));
		}
	}

	public PaymentMethod findOrFail(Long paymentMethodId) {
		return paymentMethodRepository.findById(paymentMethodId)
				.orElseThrow(() -> new PaymentMethodNotFoundException(paymentMethodId));
	}
}
