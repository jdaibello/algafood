package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.OAS_30).select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")).build().apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as cidades")).tags(new Tag("Grupos", "Gerencia os grupos"))
				.tags(new Tag("Permissões dos Grupos", "Gerencia as permissões dos grupos"))
				.tags(new Tag("Cozinhas", "Gerencia as cozinhas")).tags(new Tag("Pedidos", "Gerencia os pedidos"))
				.tags(new Tag("Fluxos dos Pedidos", "Gerencia os fluxos (status) dos pedidos"))
				.tags(new Tag("Formas de Pagamento", "Gerencia as formas de pagamento"))
				.tags(new Tag("Restaurantes", "Gerencia os restaurantes"))
				.tags(new Tag("Formas de Pagamento dos Restaurantes",
						"Gerencia as formas de pagamento dos restaurantes"))
				.tags(new Tag("Produtos dos Restaurantes", "Gerencia os produtos dos restaurantes"))
				.tags(new Tag("Fotos dos Produtos dos Restaurantes", "Gerencia as fotos dos produtos dos restaurantes"))
				.tags(new Tag("Responsáveis pelos Restaurantes",
						"Gerencia os usuários responsáveis pelos restaurantes"))
				.tags(new Tag("Estados", "Gerencia os estados"))
				.tags(new Tag("Estatísticas", "Gerencia relatórios sobre informações de vendas"))
				.tags(new Tag("Usuários", "Gerencia os usuários"))
				.tags(new Tag("Grupos de Usuários", "Gerencia os grupos de usuários"));
	}

	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Algafood API").description("API aberta para clientes e restaurantes")
				.version("1.0.0").contact(new Contact("João Pedro", "https://github.com/jdaibello/algafood", ""))
				.build();
	}
}
