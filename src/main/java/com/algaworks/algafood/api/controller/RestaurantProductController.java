package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.ProductDTOAssembler;
import com.algaworks.algafood.api.assembler.ProductInputDisassembler;
import com.algaworks.algafood.api.dto.ProductDTO;
import com.algaworks.algafood.api.dto.input.ProductInput;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController {

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

	@GetMapping
	public List<ProductDTO> fetchAll(@PathVariable Long restaurantId) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);

		return productDTOAssembler.toCollectionModel(productRepository.findByRestaurant(restaurant));
	}

	@GetMapping("/{productId}")
	public ProductDTO find(@PathVariable Long restaurantId, @PathVariable Long productId) {
		return productDTOAssembler.toModel(service.findOrFail(restaurantId, productId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductDTO add(@PathVariable Long restaurantId, @RequestBody @Valid ProductInput productInput) {
		Restaurant restaurant = restaurantService.findOrFail(restaurantId);
		Product product = productInputDisassembler.toDomainObject(productInput);
		product.setRestaurant(restaurant);
		product = service.save(product);

		return productDTOAssembler.toModel(product);
	}

	@PutMapping("/{productId}")
	public ProductDTO update(@PathVariable Long restaurantId, @PathVariable Long productId,
			@RequestBody @Valid ProductInput productInput) {
		Product currentProduct = service.findOrFail(restaurantId, productId);
		productInputDisassembler.copyToDomainObject(productInput, currentProduct);
		currentProduct = service.save(currentProduct);

		return productDTOAssembler.toModel(currentProduct);
	}
}
