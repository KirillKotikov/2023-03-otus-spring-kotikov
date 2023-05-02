package ru.kotikov.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.kotikov.providers.LocaleProvider;

@Service
@Getter
@RequiredArgsConstructor
public class MessageLocaleService {

    private final MessageSource messageSource;

    private final LocaleProvider localeProvider;
}
