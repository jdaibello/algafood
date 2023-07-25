package com.algaworks.algafood.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CityDTOAssembler;
import com.algaworks.algafood.api.assembler.CityInputDisassembler;
import com.algaworks.algafood.api.dto.CityDTO;
import com.algaworks.algafood.api.dto.input.CityInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.openapi.controller.CityControllerOpenApi;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.StateNotFoundException;
import com.algaworks.algafood.domain.model.City;
import com.algaworks.algafood.domain.repository.CityRepository;
import com.algaworks.algafood.domain.service.CityService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerOpenApi {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CityService service;

	@Autowired
	private CityDTOAssembler cityDTOAssembler;

	@Autowired
	private CityInputDisassembler cityInputDisassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CityDTO> fetchAll() {
		List<CityDTO> citiesDTO = cityDTOAssembler.toCollectionModel(cityRepository.findAll());

		citiesDTO.forEach(cityDTO -> {
			cityDTO.add(linkTo(methodOn(CityController.class).find(cityDTO.getId())).withSelfRel());
			cityDTO.add(linkTo(methodOn(CityController.class).fetchAll()).withRel("cities"));
			cityDTO.getState().add(linkTo(methodOn(StateController.class).find(cityDTO.getState().getId())).withSelfRel());
		});

		CollectionModel<CityDTO> citiesCollectionModel = CollectionModel.of(citiesDTO);

		citiesCollectionModel.add(linkTo(CityController.class).withSelfRel());

		return citiesCollectionModel;
	}

	@Override
	@GetMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CityDTO find(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cityId) {
		City city = service.findOrFail(cityId);
		CityDTO cityDTO = cityDTOAssembler.toModel(city);

		cityDTO.add(linkTo(methodOn(CityController.class).find(cityDTO.getId())).withSelfRel());
		cityDTO.add(linkTo(methodOn(CityController.class).fetchAll()).withRel("cities"));
		cityDTO.getState().add(linkTo(methodOn(StateController.class).find(cityDTO.getState().getId())).withSelfRel());

		return cityDTO;
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CityDTO add(@RequestBody @Valid CityInput cityInput) {
		try {
			City city = cityInputDisassembler.toDomainObject(cityInput);
			city = service.save(city);

			CityDTO cityDTO = cityDTOAssembler.toModel(city);
			ResourceUriHelper.addUriInResponseHeader(cityDTO.getId());

			return cityDTO;
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@PutMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CityDTO update(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cityId,
			@RequestBody @Valid CityInput city) {
		try {
			City currentCity = service.findOrFail(cityId);
			cityInputDisassembler.copyToDomainObject(city, currentCity);
			currentCity = service.save(currentCity);

			return cityDTOAssembler.toModel(currentCity);
		} catch (StateNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	@DeleteMapping("/{cityId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cityId) {
		service.delete(cityId);
	}
}
