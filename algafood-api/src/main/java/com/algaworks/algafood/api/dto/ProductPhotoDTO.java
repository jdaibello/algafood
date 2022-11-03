package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoDTO {
	private String fileName;
	private String description;

	@ApiModelProperty(example = "image/jpeg")
	private String contentType;

	@ApiModelProperty(example = "2119857")
	private Long size;
}
