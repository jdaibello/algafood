package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @GetMapping
    public RootEntryPointDTO root() {
        var rootEntryPointDTO = new RootEntryPointDTO();

        if (algaSecurity.canQueryKitchens()) {
            rootEntryPointDTO.add(algaLinks.linkToKitchens("kitchens"));
        }

        if (algaSecurity.canSearchOrders()) {
            rootEntryPointDTO.add(algaLinks.linkToOrders("orders"));
        }

        if (algaSecurity.canQueryRestaurants()) {
            rootEntryPointDTO.add(algaLinks.linkToRestaurants("restaurants"));
        }

        if (algaSecurity.canQueryUsersGroupsPermissions()) {
            rootEntryPointDTO.add(algaLinks.linkToGroups("groups"));
            rootEntryPointDTO.add(algaLinks.linkToUsers("users"));
            rootEntryPointDTO.add(algaLinks.linkToPermissions("permissions"));
        }

        if (algaSecurity.canQueryPaymentMethods()) {
            rootEntryPointDTO.add(algaLinks.linkToPaymentMethods("payment-methods"));
        }

        if (algaSecurity.canQueryStates()) {
            rootEntryPointDTO.add(algaLinks.linkToStates("states"));
        }

        if (algaSecurity.canQueryCities()) {
            rootEntryPointDTO.add(algaLinks.linkToCities("cities"));
        }

        if (algaSecurity.canQueryStatistics()) {
            rootEntryPointDTO.add(algaLinks.linkToStatistics("statistics"));
        }

        return rootEntryPointDTO;
    }

    private static class RootEntryPointDTO extends RepresentationModel<RootEntryPointDTO> {
    }
}
