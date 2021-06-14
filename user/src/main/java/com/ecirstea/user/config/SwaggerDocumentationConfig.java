package com.ecirstea.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerDocumentationConfig
{

    ApiInfo apiInfo()
    {
        return new ApiInfoBuilder()
                .title("User API")
                .description("Microservice to perform CRUD operations with an user")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("Elena Cirstea", "", "elender1230@gmail.com"))
                .build();
    }

    @Bean
    public Docket customImplementation()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ecirstea.user.controller"))
                .build()
                .directModelSubstitute(org.threeten.bp.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.threeten.bp.OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    private ApiKey apiKey()
    {
        return new ApiKey("JWT", "Authorization", "header");
    }


    private SecurityContext securityContext()
    {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private AuthorizationScope[] authorizationScopes()
    {
        return new AuthorizationScope[]{
                new AuthorizationScope("global", "write accessEverything")
        };
    }

    List<SecurityReference> defaultAuth()
    {
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes()));
    }
}
