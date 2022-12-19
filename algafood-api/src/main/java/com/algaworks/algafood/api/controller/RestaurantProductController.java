package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProductDTOAssembler;
import com.algaworks.algafood.api.assembler.ProductInputDisassembler;
import com.algaworks.algafood.api.dto.ProductDTO;
import com.algaworks.algafood.api.dto.input.ProductInput;
import com.algaworks.algafood.api.openapi.controller.RestaurantProductControllerOpenApi;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductController implements RestaurantProductControllerOpenApi {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService service;

	@Autowired
	private RestaurantService restaurantService;

	@Autowired
	private ProductDTOAssembler productDTOAssembler;

	@Autowired
	private ProductInputDisassembler productInputDisassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductDTO> fetchAll(
			@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@RequestParam(required = false) boolean includeInactive) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);
		List<Product> allProducts = null;

		if (includeInactive) {
			allProducts = productRepository.findAllByRestaurant(restaurant);
		} else {
			allProducts = productRepository.findActivesByRestaurant(restaurant);
		}

		return productDTOAssembler.toCollectionModel(allProducts);
	}

	@Override
	@GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO find(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1") @PathVariable Long productId) {
		return productDTOAssembler.toModel(service.findOrFail(restaurantId, productId));
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO add(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);
		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		product = service.save(product);

		return productDTOAssembler.toModel(product);
	}

	@Override
	@PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductDTO update(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1") @PathVariable Long productId,
			@RequestBody @Valid ProductInput productInput) {
		Product currentProduct = service.findOrFail(restaurantId, productId);
		productInputDisassembler.copyToDomainObject(productInput, currentProduct);
		currentProduct = service.save(currentProduct);

		return productDTOAssembler.toModel(currentProduct);
	}
}
