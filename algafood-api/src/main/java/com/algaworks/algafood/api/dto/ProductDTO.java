package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "products")
@Getter
@Setter
public class ProductDTO extends RepresentationModel<ProductDTO> {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "X-Tudo", required = true)
    private String name;

    @ApiModelProperty(
            example = "Pão de hambúrguer, hambúrguer caseiro, contra-filé, frango, bacon, calabresa, "
                    + "catupiry, ovo, presunto, mussarela, milho, tomate, alface e batata palha",
            required = true)
    private String description;

    @ApiModelProperty(example = "32.00", required = true)
    private BigDecimal price;

    @ApiModelProperty(example = "true", required = true)
    private Boolean active;
}
