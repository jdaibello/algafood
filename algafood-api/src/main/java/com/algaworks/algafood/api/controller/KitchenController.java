package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

import com.algaworks.algafood.api.assembler.KitchenDTOAssembler;
import com.algaworks.algafood.api.assembler.KitchenInputDisassembler;
import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.api.dto.input.KitchenInput;
import com.algaworks.algafood.api.openapi.controller.KitchenControllerOpenApi;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenService;

import io.swagger.annotations.ApiParam;

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

	@Override
	@GetMapping
	public Page<KitchenDTO> fetchAll(Pageable pageable) {
		Page<Kitchen> kitchensPage = kitchenRepository.findAll(pageable);
		List<KitchenDTO> kitchensDTO = kitchenDTOAssembler.toCollectionModel(kitchensPage.getContent());

		return new PageImpl<>(kitchensDTO, pageable, kitchensPage.getTotalElements());
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

		return kitchenDTOAssembler.toModel(kitchen);
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
