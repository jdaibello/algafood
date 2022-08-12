package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig implements WebMvcConfigurer {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30).select()
                .apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api")).build().apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Algafood API")
                .description("API aberta para clientes e restaurantes")
                .version("1.0.0")
                .contact(new Contact("Algaworks", "https://github.com/jdaibello/algafood", "contato@algaworks.com"))
                .build();
    }
}
