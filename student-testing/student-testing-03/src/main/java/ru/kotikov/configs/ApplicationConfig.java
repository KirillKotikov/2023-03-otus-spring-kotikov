package ru.kotikov.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class ApplicationConfig {
}
