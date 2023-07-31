package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantProductPhotoController;
import com.algaworks.algafood.api.dto.ProductPhotoDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.ProductPhoto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductPhotoDTOAssembler extends RepresentationModelAssemblerSupport<ProductPhoto, ProductPhotoDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public ProductPhotoDTOAssembler() {
        super(RestaurantProductPhotoController.class, ProductPhotoDTO.class);
    }

    @Override
    public ProductPhotoDTO toModel(ProductPhoto photo) {
        ProductPhotoDTO productPhotoDTO = modelMapper.map(photo, ProductPhotoDTO.class);

        productPhotoDTO.add(algaLinks.linkToProductPhoto(photo.getRestaurantId(), photo.getProduct().getId()));
        productPhotoDTO.add(algaLinks.linkToProduct(photo.getRestaurantId(), photo.getProduct().getId(), "product"));

        return productPhotoDTO;
    }
}
