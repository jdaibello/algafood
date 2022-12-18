package com.algaworks.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.ProductPhotoDTOAssembler;
import com.algaworks.algafood.api.controller.openapi.RestaurantProductPhotoControllerOpenApi;
import com.algaworks.algafood.api.dto.ProductPhotoDTO;
import com.algaworks.algafood.api.dto.input.ProductPhotoInput;
import com.algaworks.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.algafood.domain.model.Product;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.service.PhotoStorageService;
import com.algaworks.algafood.domain.service.ProductPhotoCatalogService;
import com.algaworks.algafood.domain.service.ProductService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
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
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductPhotoDTO find(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1") @PathVariable Long productId) {
		ProductPhoto productPhoto = productPhotoCatalogService.findOrFail(restaurantId, productId);

		return productPhotoDTOAssembler.toModel(productPhoto);
	}

	@Override
	@GetMapping
	public ResponseEntity<?> servePhoto(
			@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1") @PathVariable Long productId,
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
	@PutMapping(produces = MediaType.MULTIPART_FORM_DATA_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ProductPhotoDTO updatePhoto(
			@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1") @PathVariable Long productId,
			@Valid ProductPhotoInput productPhotoInput) throws IOException {
		Product product = productService.findOrFail(restaurantId, productId);
		MultipartFile file = productPhotoInput.getFile();
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
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(value = "ID do restaurante", example = "1") @PathVariable Long restaurantId,
			@ApiParam(value = "ID do produto", example = "1") @PathVariable Long productId) {
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
