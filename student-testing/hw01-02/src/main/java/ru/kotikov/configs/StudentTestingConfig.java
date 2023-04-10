package ru.kotikov.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kotikov.services.IOServiceStreams;

@Configuration
public class StudentTestingConfig {

    @Bean
    public IOServiceStreams ioServiceStreams() {
        return new IOServiceStreams(System.out, System.in);
    }
}
