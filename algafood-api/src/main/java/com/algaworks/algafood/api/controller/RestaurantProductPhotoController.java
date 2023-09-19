package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProductPhotoDTOAssembler;
import com.algaworks.algafood.api.dto.ProductPhotoDTO;
import com.algaworks.algafood.api.dto.input.ProductPhotoInput;
import com.algaworks.algafood.api.springdoc.controller.RestaurantProductPhotoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.algaworks.algafood.domain.service.ProductPhotoCatalogService;
import com.algaworks.algafood.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(
        value = "/restaurants/{restaurantId}/products/{productId}/photo",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantProductPhotoController implements RestaurantProductPhotoControllerOpenApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductPhotoCatalogService productPhotoCatalogService;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private ProductPhotoDTOAssembler productPhotoDTOAssembler;

    @Override
    @CheckSecurity.Restaurants.CanQuery
    @SecurityRequirement(name = "OAuth2")
    public ProductPhotoDTO find(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                                @Parameter(description = "ID do produto", example = "1") @PathVariable Long productId) {
        ProductPhoto productPhoto = productPhotoCatalogService.findOrFail(restaurantId, productId);

        return productPhotoDTOAssembler.toModel(productPhoto);
    }

    @Override
    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servePhoto(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long productId,
            @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            ProductPhoto productPhoto = productPhotoCatalogService.findOrFail(restaurantId, productId);
            MediaType photoMediaType = MediaType.parseMediaType(productPhoto.getContentType());
            List<MediaType> acceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);

            verifyMediaTypeCompatibility(photoMediaType, acceptedMediaTypes);

            PhotoStorageService.RecoveredPhoto recoveredPhoto = photoStorageService.recover(productPhoto.getFileName());

            if (recoveredPhoto.hasUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, recoveredPhoto.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok().contentType(photoMediaType)
                        .body(new InputStreamResource(recoveredPhoto.getInputStream()));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @SecurityRequirement(name = "OAuth2")
    @CheckSecurity.Restaurants.CanManageOperation
    @PutMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPhotoDTO updatePhoto(
            @Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
            @Parameter(description = "ID do produto", example = "1") @PathVariable Long productId,
            @Valid ProductPhotoInput productPhotoInput, @RequestPart(required = true) MultipartFile file)
            throws IOException {
        Product product = productService.findOrFail(restaurantId, productId);
        ProductPhoto photo = new ProductPhoto();

        photo.setProduct(product);
        photo.setDescription(productPhotoInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        ProductPhoto savedPhoto = productPhotoCatalogService.save(photo, file.getInputStream());

        return productPhotoDTOAssembler.toModel(savedPhoto);
    }

    @Override
    @CheckSecurity.Restaurants.CanManageOperation
    @SecurityRequirement(name = "OAuth2")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
                       @Parameter(description = "ID do produto", example = "1") @PathVariable Long productId) {
        productPhotoCatalogService.delete(restaurantId, productId);
    }

    private void verifyMediaTypeCompatibility(MediaType photoMediaType, List<MediaType> acceptedMediaTypes)
            throws HttpMediaTypeNotAcceptableException {
        boolean compatible = acceptedMediaTypes.stream()
                .anyMatch(acceptedMediaType -> acceptedMediaType.isCompatibleWith(photoMediaType));

        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(acceptedMediaTypes);
        }
    }
}
