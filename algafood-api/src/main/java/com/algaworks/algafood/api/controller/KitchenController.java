package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.api.dto.input.KitchenInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.domain.model.Kitchen;
import com.algaworks.algafood.domain.repository.KitchenRepository;
import com.algaworks.algafood.domain.service.KitchenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
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

	@ApiOperation("Listar com paginação")
	@GetMapping
	public Page<KitchenDTO> fetchAll(Pageable pageable) {
		Page<Kitchen> kitchensPage = kitchenRepository.findAll(pageable);
		List<KitchenDTO> kitchensDTO = kitchenDTOAssembler.toCollectionModel(kitchensPage.getContent());

		return new PageImpl<>(kitchensDTO, pageable, kitchensPage.getTotalElements());
	}

	@ApiOperation("Buscar por ID")
	@ApiResponses({
			@ApiResponse(
				responseCode = "400",
				description = "ID da cozinha inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Cozinha não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@GetMapping("/{kitchenId}")
	public KitchenDTO find(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long kitchenId) {
		Kitchen kitchen = service.findOrFail(kitchenId);

		return kitchenDTOAssembler.toModel(kitchen);
	}

	@ApiOperation("Adicionar")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Cozinha cadastrada") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public KitchenDTO add(@RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen kitchen = kitchenInputDisassembler.toDomainObject(kitchenInput);
		kitchen = service.save(kitchen);

		return kitchenDTOAssembler.toModel(kitchen);
	}

	@ApiOperation("Atualizar")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
			@ApiResponse(
				responseCode = "400",
				description = "ID da cozinha inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Cidade não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@PutMapping("/{kitchenId}")
	public KitchenDTO update(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long kitchenId,
			@RequestBody @Valid KitchenInput kitchenInput) {
		Kitchen currentKitchen = service.findOrFail(kitchenId);
		kitchenInputDisassembler.copyToDomainObject(kitchenInput, currentKitchen);
		currentKitchen = service.save(currentKitchen);

		return kitchenDTOAssembler.toModel(currentKitchen);
	}

	@ApiOperation("Excluir")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
			@ApiResponse(
				responseCode = "400",
				description = "ID da cozinha inválido",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
			@ApiResponse(
				responseCode = "404",
				description = "Cidade não encontrada",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))) })
	@DeleteMapping("/{kitchenId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(value = "ID da cozinha", example = "1") @PathVariable Long kitchenId) {
		service.delete(kitchenId);
	}
}
