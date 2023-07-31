package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UserDTOAssembler;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.openapi.controller.RestaurantResponsibleUserControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/responsibles", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResponsibleUserController implements RestaurantResponsibleUserControllerOpenApi {

    @Autowired
    private RestaurantService service;

    @Autowired
    private UserDTOAssembler userDTOAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UserDTO> fetchAll(
            @ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        Restaurant restaurant = service.findOrFail(restaurantId);

        CollectionModel<UserDTO> usersDTO = userDTOAssembler.toCollectionModel(restaurant.getResponsibles())
                .removeLinks().add(algaLinks.linkToRestaurantResponsible(restaurantId))
                .add(algaLinks.linkToRestaurantResponsibleAttachment(restaurantId, "attach"));

        usersDTO.getContent().stream().forEach(userDTO -> {
            userDTO.add(algaLinks.linkToRestaurantResponsibleDetachment(restaurantId, userDTO.getId(), "detach"));
        });

        return usersDTO;
    }

    @Override
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
        service.attachResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detach(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                                       @ApiParam(value = "ID do usuário", example = "1") @PathVariable Long userId) {
        service.detachResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
