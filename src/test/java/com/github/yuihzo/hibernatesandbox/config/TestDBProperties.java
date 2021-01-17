package com.github.yuihzo.hibernatesandbox.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.datasource")
@ConstructorBinding
@AllArgsConstructor
@Getter
public class TestDBProperties {
    private final String url;
    private final String username;
    private final String password;
}
