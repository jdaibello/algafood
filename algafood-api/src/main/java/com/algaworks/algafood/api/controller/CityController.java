package com.algaworks.algafood.api.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return cityDTOAssembler.toCollectionModel(cityRepository.findAll());
    }

    @Override
    @GetMapping(value = "/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityDTO find(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cityId) {
        City city = service.findOrFail(cityId);

        return cityDTOAssembler.toModel(city);
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
