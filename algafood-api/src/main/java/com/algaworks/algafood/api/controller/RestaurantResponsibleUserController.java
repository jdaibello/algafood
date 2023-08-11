package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UserDTOAssembler;
import com.algaworks.algafood.api.dto.UserDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.springdoc.controller.RestaurantResponsibleUserControllerOpenApi;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Autowired
    private AlgaSecurity algaSecurity;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<UserDTO> fetchAll(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId) {
        Restaurant restaurant = service.findOrFail(restaurantId);

        CollectionModel<UserDTO> usersDTO = userDTOAssembler.toCollectionModel(restaurant.getResponsibles())
                .removeLinks();

        usersDTO.add(algaLinks.linkToRestaurantResponsible(restaurantId));

        if (algaSecurity.canManageRestaurantsRegistration()) {
            usersDTO.add(algaLinks.linkToRestaurantResponsibleAttachment(restaurantId, "attach"));

            usersDTO.getContent().stream().forEach(userDTO -> {
                userDTO.add(algaLinks.linkToRestaurantResponsibleDetachment(restaurantId, userDTO.getId(), "detach"));
            });
        }

        return usersDTO;
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegistration
    @SecurityRequirement(name = "OAuth2")
    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> attach(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @Parameter(description = "ID do usuário", example = "1") @PathVariable Long userId) {
        service.attachResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @CheckSecurity.Restaurants.CanManageRegistration
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> detach(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                                       @Parameter(description = "ID do usuário", example = "1") @PathVariable Long userId) {
        service.detachResponsible(restaurantId, userId);

        return ResponseEntity.noContent().build();
    }
}
