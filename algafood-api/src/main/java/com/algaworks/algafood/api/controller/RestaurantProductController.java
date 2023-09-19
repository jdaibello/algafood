package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProductDTOAssembler;
import com.algaworks.algafood.api.assembler.ProductInputDisassembler;
import com.algaworks.algafood.api.dto.ProductDTO;
import com.algaworks.algafood.api.dto.input.ProductInput;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.api.helper.ResourceUriHelper;
import com.algaworks.algafood.api.springdoc.controller.RestaurantProductControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.Restaurant;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.ProductService;
import com.algaworks.algafood.domain.service.RestaurantService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<ProductDTO> fetchAll(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
            @RequestParam(required = false) Boolean includeInactive) {
        Restaurant restaurant = restaurantService.findOrFail(restaurantId);
        List<Product> allProducts = null;

        if (includeInactive) {
            allProducts = productRepository.findAllByRestaurant(restaurant);
        } else {
            allProducts = productRepository.findActivesByRestaurant(restaurant);
        }

        return productDTOAssembler.toCollectionModel(allProducts).add(algaLinks.linkToProducts(restaurantId));
    }

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @SecurityRequirement(name = "OAuth2")
    @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO find(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                           @Parameter(description = "ID do produto", example = "1") @PathVariable Long productId) {
        return productDTOAssembler.toModel(service.findOrFail(restaurantId, productId));
    }

    @Override
    @CheckSecurity.Restaurants.CanManageOperation
    @SecurityRequirement(name = "OAuth2")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO add(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                          @RequestBody @Valid ProductInput productInput) {
        Restaurant restaurant = restaurantService.findOrFail(restaurantId);
        Product product = productInputDisassembler.toDomainObject(productInput);
        product.setRestaurant(restaurant);
        product = service.save(product);

        ProductDTO productDTO = productDTOAssembler.toModel(product);
        ResourceUriHelper.addUriInResponseHeader(productDTO.getId());

        return productDTO;
    }

    @Override
    @CheckSecurity.Restaurants.CanManageOperation
    @SecurityRequirement(name = "OAuth2")
    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO update(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                             @Parameter(description = "ID do produto", example = "1") @PathVariable Long productId,
                             @RequestBody @Valid ProductInput productInput) {
        Product currentProduct = service.findOrFail(restaurantId, productId);
        productInputDisassembler.copyToDomainObject(productInput, currentProduct);
        currentProduct = service.save(currentProduct);

        return productDTOAssembler.toModel(currentProduct);
    }
}
