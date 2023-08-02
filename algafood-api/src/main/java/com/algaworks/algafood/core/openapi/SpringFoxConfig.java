package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.dto.CityDTO;
import com.algaworks.algafood.api.dto.KitchenDTO;
import com.algaworks.algafood.api.dto.OrderSummaryDTO;
import com.algaworks.algafood.api.dto.StateDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.openapi.model.*;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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
                .additionalModels(typeResolver.resolve(Problem.class))
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .directModelSubstitute(Links.class, LinksModelOpenApi.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, KitchenDTO.class),
                        KitchensModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Page.class, OrderSummaryDTO.class),
                        OrderSummariesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class,
                        CityDTO.class), CitiesModelOpenApi.class))
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class,
                        StateDTO.class), StatesModelOpenApi.class))
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        Resource.class, File.class, InputStream.class)
                .apiInfo(apiInfo()).tags(new Tag("Cidades", "Gerencia as cidades"))
                .tags(new Tag("Grupos", "Gerencia os grupos"))
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
                .tags(new Tag("Grupos de Usuários", "Gerencia os grupos de usuários"))
                .tags(new Tag("Permissões", "Gerencia as permissões"));
    }

    private List<Response> globalGetResponseMessages() {
        return Arrays.asList(new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor").representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference()).build(),
                new ResponseBuilder().code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor").build());
    }

    private List<Response> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida - erro do cliente").representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference()).build(),
                new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor").representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference()).build(),
                new ResponseBuilder().code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
                        .description("Recurso não possui representação que pode ser aceita pelo consumidor").build(),
                new ResponseBuilder().code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
                        .description("Requisição recusada porque o corpo está em um formato não suportado")
                        .representation(MediaType.APPLICATION_JSON).apply(getProblemModelReference()).build());
    }

    private List<Response> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseBuilder().code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .description("Requisição inválida - erro do cliente").representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference()).build(),
                new ResponseBuilder().code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do servidor").representation(MediaType.APPLICATION_JSON)
                        .apply(getProblemModelReference()).build());
    }

    private Consumer<RepresentationBuilder> getProblemModelReference() {
        return r -> r.model(m -> m.name("Problem").referenceModel(ref -> ref.key(k -> k
                .qualifiedModelName(q -> q.name("Problem").namespace("com.algaworks.algafood.api.exceptionhandler")))));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Algafood API").description("API aberta para clientes e restaurantes")
                .version("1.0.0").contact(new Contact("João Pedro", "https://github.com/jdaibello/algafood", ""))
                .build();
    }
}
