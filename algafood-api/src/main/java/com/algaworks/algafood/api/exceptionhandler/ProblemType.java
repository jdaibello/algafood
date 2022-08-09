package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	INCOMPREHENSIBLE_MESSAGE("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
	ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
	BUSINESS_ERROR("/violacao-regra-negocio", "Violação de regra de negócio"),
	INVALID_PARAMETER("/parametro-invalido", "Parâmetro inválido"),
	SYSTEM_ERROR("/erro-do-sistema", "Erro do sistema"), 
	INVALID_DATA("/dados-invalidos", "Dados inválidos");

	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "http://localhost:8080" + path;
		this.title = title;
	}
}
