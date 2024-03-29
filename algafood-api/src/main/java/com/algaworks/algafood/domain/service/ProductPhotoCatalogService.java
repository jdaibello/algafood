package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProductPhotoNotFoundException;
import com.algaworks.algafood.domain.model.ProductPhoto;
import com.algaworks.algafood.domain.repository.ProductRepository;
import com.algaworks.algafood.domain.service.PhotoStorageService.NewPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class ProductPhotoCatalogService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhotoStorageService photoStorageService;

    public ProductPhoto findOrFail(Long restaurantId, Long productId) {
        return productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new ProductPhotoNotFoundException(restaurantId, productId));
    }

    @Transactional
    public ProductPhoto save(ProductPhoto photo, InputStream fileData) {
        Long restaurantId = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();
        String newFileName = photoStorageService.generateFileName(photo.getFileName());
        String existingFileName = null;

        Optional<ProductPhoto> existingPhoto = productRepository.findPhotoById(restaurantId, productId);
        if (existingPhoto.isPresent()) {
            existingFileName = existingPhoto.get().getFileName();
            productRepository.delete(existingPhoto.get());
        }

        photo.setFileName(newFileName);
        photo = productRepository.save(photo);
        productRepository.flush();

        NewPhoto newPhoto = NewPhoto.builder().fileName(photo.getFileName()).contentType(photo.getContentType())
                .inputStream(fileData).build();

        photoStorageService.substitute(existingFileName, newPhoto);

        return photo;
    }

    @Transactional
    public void delete(Long restaurantId, Long productId) {
        ProductPhoto photo = findOrFail(restaurantId, productId);

        productRepository.delete(photo);
        productRepository.flush();

        photoStorageService.remove(photo.getFileName());
    }
}
