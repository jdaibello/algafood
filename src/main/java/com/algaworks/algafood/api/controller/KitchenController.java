package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.algaworks.algafood.api.model.KitchenDTO;
import com.algaworks.algafood.api.model.input.KitchenInput;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenService;

@RestController
@RequestMapping("/kitchens")
public class KitchenController {

	@Autowired
	private KitchenRepository kitchenRepository;

	@Autowired
	private KitchenService service;

	@Autowired
	private KitchenDTOAssembler kitchenDTOAssembler;

	@Autowired
	private KitchenInputDisassembler kitchenInputDisassembler;

	@GetMapping
	public List<KitchenDTO> fetchAll() {
		return kitchenDTOAssembler.toCollectionModel(kitchenRepository.findAll());
	}

	@GetMapping("/{kitchenId}")
	public KitchenDTO find(@PathVariable Long kitchenId) {
		Kitchen kitchen = service.findOrFail(kitchenId);

		return kitchenDTOAssembler.toModel(kitchen);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenDTO add(@RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);
		kitchen = service.save(kitchen);

		return kitchenDTOAssembler.toModel(kitchen);
	}

	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen currentKitchen = service.findOrFail(kitchenId);
		kitchenInputDisassembler.copyToDomainObject(kitchenInput, currentKitchen);
		currentKitchen = service.save(currentKitchen);

		return kitchenDTOAssembler.toModel(currentKitchen);
	}

	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long kitchenId) {
		service.delete(kitchenId);
	}
}
