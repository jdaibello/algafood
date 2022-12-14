package com.algaworks.algafood.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoDTO {

	@ApiModelProperty(required = true)
	private String fileName;

	private String description;

	@ApiModelProperty(example = "image/jpeg", required = true)
	private String contentType;

	@ApiModelProperty(example = "2119857", required = true)
	private Long size;
}
