package com.algaworks.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer {

	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}

	@Bean
	public Docket apiDocket() {
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.OAS_30).select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")).build()
				.useDefaultResponseMessages(false).globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class)).apiInfo(apiInfo())
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

	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor").build(),
				new ResponseBuilder().code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
						.description("Recurso não possui representação que pode ser aceita pelo consumidor").build());
	}

	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						.description("Requisição inválida - erro do cliente").build(),
				new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor").build(),
				new ResponseBuilder().code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
						.description("Recurso não possui representação que pode ser aceita pelo consumidor").build(),
				new ResponseBuilder().code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
						.description("Requisição recusada porque o corpo está em um formato não suportado").build());
	}

	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
						.description("Requisição inválida - erro do cliente").build(),
				new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
						.description("Erro interno do servidor").build());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Algafood API").description("API aberta para clientes e restaurantes")
				.version("1.0.0").contact(new Contact("João Pedro", "https://github.com/jdaibello/algafood", ""))
				.build();
	}
}
