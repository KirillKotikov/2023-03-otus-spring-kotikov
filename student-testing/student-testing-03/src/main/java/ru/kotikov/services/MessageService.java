package ru.kotikov.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.kotikov.providers.LocaleProvider;
import ru.kotikov.providers.QuestionParamsProvider;

@Service
@RequiredArgsConstructor
@Getter
public class MessageService {

    private final LocaleProvider localeProvider;

    private final QuestionParamsProvider questionParamsProvider;

    private final MessageSource messageSource;

}
