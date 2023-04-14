package ru.kotikov.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private Locale locale;
}
