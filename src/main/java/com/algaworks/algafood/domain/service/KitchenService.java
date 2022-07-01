package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;

@Service
public class KitchenService {
	private static final String KITCHEN_IN_USE_MSG = "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	private KitchenRepository kitchenRepository;

	@Transactional
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	@Transactional
	public void delete(Long cityId) {
		try {
			kitchenRepository.deleteById(cityId);

		} catch (EmptyResultDataAccessException e) {
			throw new KitchenNotFoundException(cityId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(KITCHEN_IN_USE_MSG, cityId));
		}
	}

	public Kitchen findOrFail(Long cityId) {
		return kitchenRepository.findById(cityId).orElseThrow(() -> new KitchenNotFoundException(cityId));
	}
}
