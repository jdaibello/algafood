package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UserDTOAssembler;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.openapi.controller.RestaurantResponsibleUserControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsibles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResponsibleUserController implements RestaurantResponsibleUserControllerOpenApi {

    @Autowired
    private RestaurantService service;

    @Autowired
    private UserDTOAssembler userDTOAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UserDTO> fetchAll(
            @ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        Restaurant restaurant = service.findOrFail(restaurantId);

        return userDTOAssembler.toCollectionModel(restaurant.getResponsibles()).removeLinks()
                .add(linkTo(methodOn(RestaurantResponsibleUserController.class).fetchAll(restaurantId)).withSelfRel());
    }

    @Override
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
        service.attachResponsible(restaurantId, userId);
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
        service.detachResponsible(restaurantId, userId);
    }
}
