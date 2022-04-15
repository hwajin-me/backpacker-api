package com.idus.backpacker.api.kernel.application.config.openapi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "openapi.docs")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class OpenApiDocsProperties {
    private final String title;
    private final String description;
    private final String version;
}
