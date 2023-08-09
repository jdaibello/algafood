package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestaurantBasicDTOAssembler;
import com.algaworks.algafood.api.assembler.RestaurantDTOAssembler;
import com.algaworks.algafood.api.assembler.RestaurantInputDisassembler;
import com.algaworks.algafood.api.assembler.RestaurantOnlyNameDTOAssembler;
import com.algaworks.algafood.api.dto.RestaurantBasicDTO;
import com.algaworks.algafood.api.dto.RestaurantDTO;
import com.algaworks.algafood.api.dto.RestaurantOnlyNameDTO;
import com.algaworks.algafood.api.dto.input.RestaurantInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.openapi.controller.RestaurantControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.BusinessException;
import com.algaworks.algafood.domain.exception.CityNotFoundException;
import com.algaworks.algafood.domain.exception.KitchenNotFoundException;
import com.algaworks.algafood.domain.exception.RestaurantNotFoundException;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.RestaurantRepository;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerOpenApi {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService service;

    @Autowired
    private RestaurantDTOAssembler restaurantDTOAssembler;

    @Autowired
    private RestaurantInputDisassembler restaurantInputDisassembler;

    @Autowired
    private RestaurantBasicDTOAssembler restaurantBasicDTOAssembler;

    @Autowired
    private RestaurantOnlyNameDTOAssembler restaurantOnlyNameDTOAssembler;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping
    public CollectionModel<RestaurantBasicDTO> fetchAll() {
        return restaurantBasicDTOAssembler.toCollectionModel(restaurantRepository.findAll());
    }

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping(params = "projection=only-name")
    public CollectionModel<RestaurantOnlyNameDTO> fetchAllOnlyNames() {
        return restaurantOnlyNameDTOAssembler.toCollectionModel(restaurantRepository.findAll());
    }

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @GetMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDTO find(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        Restaurant restaurant = service.findOrFail(restaurantId);

        return restaurantDTOAssembler.toModel(restaurant);
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDTO add(@RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant restaurant = restaurantInputDisassembler.toDomainObject(restaurantInput);

            RestaurantDTO restaurantDTO = restaurantDTOAssembler.toModel(service.save(restaurant));
            ResourceUriHelper.addUriInResponseHeader(restaurantDTO.getId());

            return restaurantDTO;
        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @PutMapping(value = "/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantDTO update(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                                @RequestBody @Valid RestaurantInput restaurantInput) {
        try {
            Restaurant currentRestaurant = service.findOrFail(restaurantId);
            restaurantInputDisassembler.copyToDomainObject(restaurantInput, currentRestaurant);

            return restaurantDTOAssembler.toModel(service.save(currentRestaurant));
        } catch (KitchenNotFoundException | CityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> activate(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        service.activate(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @DeleteMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactivate(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        service.inactivate(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @PutMapping(value = "/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            service.activate(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @DeleteMapping(value = "/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactivateMultiples(@RequestBody List<Long> restaurantIds) {
        try {
            service.inactivate(restaurantIds);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/{restaurantId}/opening")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> open(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        service.open(restaurantId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanEdit
    @PutMapping("/{restaurantId}/closing")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> close(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        service.close(restaurantId);

        return ResponseEntity.noContent().build();
    }
}
