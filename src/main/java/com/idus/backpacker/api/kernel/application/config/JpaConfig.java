package com.idus.backpacker.api.kernel.application.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.idus.backpacker.core"})
@EntityScan(basePackages = {"com.idus.backpacker.core"})
public class JpaConfig {}
