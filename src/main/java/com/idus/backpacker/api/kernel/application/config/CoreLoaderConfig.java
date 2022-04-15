package com.idus.backpacker.api.kernel.application.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.idus.backpacker.core"})
@ConfigurationPropertiesScan(basePackages = {"com.idus.backpacker.core"})
public class CoreLoaderConfig {}
