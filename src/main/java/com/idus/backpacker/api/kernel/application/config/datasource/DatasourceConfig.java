package com.idus.backpacker.api.kernel.application.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DatasourceConfig {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.primary.hikari")
    DataSource primaryDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.reader.hikari")
    DataSource readerDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    DataSource dynamicDataSource() {
        final var router = new DatasourceRouter();
        router.setTargetDataSources(
                Map.of(
                        DatasourceType.PRIMARY, primaryDataSource(),
                        DatasourceType.READER, readerDataSource()));
        router.setDefaultTargetDataSource(primaryDataSource());

        return router;
    }

    @Bean
    DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(dynamicDataSource());
    }
}
