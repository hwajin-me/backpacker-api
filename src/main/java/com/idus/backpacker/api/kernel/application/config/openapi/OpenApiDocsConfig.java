package com.idus.backpacker.api.kernel.application.config.openapi;

import com.fasterxml.classmate.TypeResolver;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RequiredArgsConstructor
public class OpenApiDocsConfig {
    private static final String BASE_PACKAGE = "com.idus.backpacker.api";
    private final OpenApiDocsProperties properties;
    private final TypeResolver typeResolver;

    @Bean
    Docket api() {
        var authenticationScheme =
                HttpAuthenticationScheme.JWT_BEARER_BUILDER.name("Authorization").build();

        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .alternateTypeRules(
                        AlternateTypeRules.newRule(
                                typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Collections.singletonList(authenticationScheme));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(this.properties.getTitle())
                .description(this.properties.getDescription())
                .version(this.properties.getDescription())
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("User", "UserAccess");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    @Getter
    @Setter
    @ApiModel
    public static class Page {
        @ApiModelProperty(value = "페이지", allowEmptyValue = true)
        private Integer page;

        @ApiModelProperty(value = "페이지당 원소의 개수", allowableValues = "range[0, 100]")
        private Integer size;

        @ApiModelProperty(value = "정렬 (사용법: FIELD,(ASC|DESC))")
        private List<String> sort;
    }
}
