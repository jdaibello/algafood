package com.algaworks.algafood.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "photos")
@Getter
@Setter
public class ProductPhotoDTO extends RepresentationModel<ProductPhotoDTO> {

    @Schema(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg", required = true)
    private String fileName;

    @Schema(example = "Prime Rib ao ponto", required = true)
    private String description;

    @Schema(example = "image/jpeg", required = true)
    private String contentType;

    @Schema(example = "2119857", required = true)
    private Long size;
}
