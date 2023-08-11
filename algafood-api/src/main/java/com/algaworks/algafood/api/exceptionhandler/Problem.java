package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @Schema(description = "400")
    private Integer status;

    @Schema(description = "2022-12-01T15:54:08.91755Z")
    private OffsetDateTime timestamp;

    @Schema(description = "http://localhost:8080/dados-invalidos")
    private String type;

    @Schema(description = "Dados inválidos")
    private String title;

    @Schema(description = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String detail;

    @Schema(description = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(description = "Lista de objetos ou campos que geraram o erro (opcional)")
    private List<Object> objects;

    @Schema(name = "ObjectProblem")
    @Getter
    @Builder
    public static class Object {

        @Schema(example = "price")
        private String name;

        @Schema(example = "O preço é obrigatório")
        private String userMessage;
    }
}
