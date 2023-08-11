package com.algaworks.algafood.api.springdoc.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(name = "Pageable")
@Getter
@Setter
public class PageableModelOpenApi {

    @Schema(example = "0", description = "Número da página - começa em 0")
    private int page;

    @Schema(example = "10", description = "Quantidade de registros por página")
    private int size;

    @Schema(example = "nome,asc", description = "Nome da propriedade para ordenação")
    private List<String> sort;
}
