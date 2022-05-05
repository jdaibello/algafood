package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.model.State;
import com.algaworks.algafood.domain.repository.CityRepository;

@Service
public class CityService {
	private static final String CITY_IN_USE_MSG = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateService stateService;

	public City save(City city) {
		Long stateId = city.getState().getId();
		State state = stateService.findOrFail(stateId);

		city.setState(state);
		return cityRepository.save(city);
	}

	public void delete(Long cityId) {
		try {
			cityRepository.deleteById(cityId);

		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(CITY_IN_USE_MSG, cityId));
		}
	}

	public City findOrFail(Long cityId) {
		return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
	}
}
