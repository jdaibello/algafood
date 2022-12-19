package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoDTO {

	@ApiModelProperty(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
	private String fileName;

	@ApiModelProperty(example = "Prime Rib ao ponto")
	private String description;

	@ApiModelProperty(example = "image/jpeg")
	private String contentType;

	@ApiModelProperty(example = "2119857")
	private Long size;
}
