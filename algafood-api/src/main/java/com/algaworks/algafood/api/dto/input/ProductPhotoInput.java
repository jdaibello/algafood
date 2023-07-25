package com.algaworks.algafood.api.dto.input;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductPhotoInput {

    @ApiModelProperty(hidden = true)
    @NotNull
    @FileSize(max = "5MB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile file;

    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    @NotBlank
    private String description;
}
