package com.algaworks.algafood.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoDTO {
	private String fileName;
	private String description;
	private String contentType;
	private Long size;
}
