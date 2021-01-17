package com.github.yuihzo.hibernatesandbox.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(TestDBProperties.class)
public class TestDBConfig {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/test",
                "test",
                "password"
        );
    }
}
