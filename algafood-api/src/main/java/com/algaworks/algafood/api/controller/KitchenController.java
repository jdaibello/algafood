package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.KitchenDTOAssembler;
import com.algaworks.algafood.api.assembler.KitchenInputDisassembler;
import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.api.dto.input.KitchenInput;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.openapi.controller.KitchenControllerOpenApi;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Autowired
    private KitchenService service;

    @Autowired
    private KitchenDTOAssembler kitchenDTOAssembler;

    @Autowired
    private KitchenInputDisassembler kitchenInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @Override
    @GetMapping
    public PagedModel<KitchenDTO> fetchAll(Pageable pageable) {
        Page<Kitchen> kitchensPage = kitchenRepository.findAll(pageable);

        return pagedResourcesAssembler.toModel(kitchensPage, kitchenDTOAssembler);
    }

    @Override
    @GetMapping(value = "/{kitchenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KitchenDTO find(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long kitchenId) {
        Kitchen kitchen = service.findOrFail(kitchenId);

        return kitchenDTOAssembler.toModel(kitchen);
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenDTO add(@RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);
        kitchen = service.save(kitchen);

        KitchenDTO kitchenDTO = kitchenDTOAssembler.toModel(kitchen);
        ResourceUriHelper.addUriInResponseHeader(kitchenDTO.getId());

        return kitchenDTO;
    }

    @Override
    @PutMapping(value = "/{kitchenId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KitchenDTO update(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long kitchenId,
                             @RequestBody @Valid KitchenInput kitchenInput) {
        Kitchen currentKitchen = service.findOrFail(kitchenId);
        kitchenInputDisassembler.copyToDomainObject(kitchenInput, currentKitchen);
        currentKitchen = service.save(currentKitchen);

        return kitchenDTOAssembler.toModel(currentKitchen);
    }

    @Override
    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long kitchenId) {
        service.delete(kitchenId);
    }
}
