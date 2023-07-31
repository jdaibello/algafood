package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.RestaurantProductController;
import com.algaworks.algafood.api.dto.ProductDTO;
import com.algaworks.algafood.api.helper.AlgaLinks;
import com.algaworks.algafood.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOAssembler extends RepresentationModelAssemblerSupport<Product, ProductDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public ProductDTOAssembler() {
        super(RestaurantProductController.class, ProductDTO.class);
    }

    @Override
    public ProductDTO toModel(Product product) {
        ProductDTO productDTO = createModelWithId(product.getId(), product, product.getRestaurant().getId());
        modelMapper.map(product, productDTO);

        productDTO.add(algaLinks.linkToProducts(product.getRestaurant().getId(), "products"));

        return productDTO;
    }
}
