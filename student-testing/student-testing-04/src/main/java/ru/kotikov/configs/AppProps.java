package ru.kotikov.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.kotikov.providers.LocaleProvider;
import ru.kotikov.providers.QuestionParamsProvider;
import ru.kotikov.providers.ResourceProvider;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps implements LocaleProvider, QuestionParamsProvider, ResourceProvider {
    private Locale locale;

    private int minCorrectAnswers;

    private String questionsFilePath;
}
