package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.helper.AlgaLinks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public RootEntryPointDTO root() {
        var rootEntryPointDTO = new RootEntryPointDTO();

        rootEntryPointDTO.add(algaLinks.linkToKitchens("kitchens"));
        rootEntryPointDTO.add(algaLinks.linkToOrders("orders"));
        rootEntryPointDTO.add(algaLinks.linkToRestaurants("restaurants"));
        rootEntryPointDTO.add(algaLinks.linkToGroups("groups"));
        rootEntryPointDTO.add(algaLinks.linkToUsers("users"));
        rootEntryPointDTO.add(algaLinks.linkToPermissions("permissions"));
        rootEntryPointDTO.add(algaLinks.linkToPaymentMethods("payment-methods"));
        rootEntryPointDTO.add(algaLinks.linkToStates("states"));
        rootEntryPointDTO.add(algaLinks.linkToCities("cities"));
        rootEntryPointDTO.add(algaLinks.linkToStatistics("statistics"));

        return rootEntryPointDTO;
    }

    private static class RootEntryPointDTO extends RepresentationModel<RootEntryPointDTO> {
    }
}
