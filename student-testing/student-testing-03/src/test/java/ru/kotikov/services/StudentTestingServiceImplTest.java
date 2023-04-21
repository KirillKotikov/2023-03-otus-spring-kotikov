package ru.kotikov.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import ru.kotikov.configs.AppProps;
import ru.kotikov.dao.QuestionDaoImpl;
import ru.kotikov.providers.LocaleProvider;
import ru.kotikov.providers.QuestionParamsProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Сервис по тестированию студентов ")
public class StudentTestingServiceImplTest {


    private QuestionDaoImpl questionDaoImpl;

    private final LocaleProvider localeProvider = mock(LocaleProvider.class);

    private final QuestionParamsProvider questionParamsProvider = mock(QuestionParamsProvider.class);

    private final MessageSource messageSource = mock(MessageSource.class);

    private final MessageService messageService = new MessageService(localeProvider, questionParamsProvider,
            messageSource);

    @BeforeEach
    public void initialize() {
        when(localeProvider.getLocale()).thenReturn(Locale.ENGLISH);
        when(questionParamsProvider.getMinCorrectAnswers()).thenReturn(3);
        when(messageSource.getMessage("questionsPath", null, Locale.ENGLISH)).thenReturn("src/test/resources/questions_en.csv");
        when(messageService.getMessageSource().getMessage("getFirstName", null, Locale.ENGLISH)).thenReturn("Enter your first name: ");
        when(messageService.getMessageSource().getMessage("getLastName", null, Locale.ENGLISH)).thenReturn("Enter your last name: ");
        when(messageService.getMessageSource().getMessage("answer", null, Locale.ENGLISH)).thenReturn("Enter an answer: ");
        when(messageService.getMessageSource().getMessage("testPassed", null, Locale.ENGLISH)).thenReturn("Test passed, count of correct answers = %d.");
        when(messageService.getMessageSource().getMessage("testFailed", null, Locale.ENGLISH)).thenReturn("Test failed, count of correct answers = %d. Min count of correct answers = %d.");
        when(messageService.getMessageSource().getMessage("testResults", null, Locale.ENGLISH)).thenReturn("Test results of student %s %s:");
        when(messageService.getMessageSource().getMessage("questionResult", null, Locale.ENGLISH)).thenReturn("Question №%s: your answer = %s, correct = %s");
        questionDaoImpl = new QuestionDaoImpl(messageService);
    }

    private final String OUTPUT =
            String.format(
                    "Enter your first name: %1$s" +
                            "Enter your last name: %1$s" +
                            "What is the latest version of Java?%1$s" +
                            "1) 17%1$s" +
                            "2) 23%1$s" +
                            "3) 99%1$s" +
                            "4) 20%1$s" +
                            "Enter an answer: %1$s" +
                            "What is the maximum complexity of a boolean expression allowed?%1$s" +
                            "1) 2%1$s" +
                            "2) 4%1$s" +
                            "3) 64%1$s" +
                            "4) 8%1$s" +
                            "Enter an answer: %1$s" +
                            "What is the style of writing a class name in Java?%1$s" +
                            "1) CamelCase%1$s" +
                            "2) lowerCamelCase%1$s" +
                            "3) snake_case%1$s" +
                            "4) dog@case%1$s" +
                            "Enter an answer: %1$s" +
                            "Select an example of a valid constant in Java.%1$s" +
                            "1) initial Const const%1$s" +
                            "2) String CONST%1$s" +
                            "3) static final String const%1$s" +
                            "4) static final String CONST%1$s" +
                            "Enter an answer: %1$s" +
                            "If the value of the field does not change during the execution of the program, then it should be?%1$s" +
                            "1) public%1$s" +
                            "2) static%1$s" +
                            "3) final%1$s" +
                            "4) Destroyed! :)%1$s" +
                            "Enter an answer: %1$s" +
                            "Test results of student Kirill Kotikov:%1$s" +
                            "Question №1: your answer = 4, correct = 4%1$s" +
                            "Question №2: your answer = 2, correct = 2%1$s" +
                            "Question №3: your answer = 4, correct = 1%1$s" +
                            "Question №4: your answer = 4, correct = 4%1$s" +
                            "Question №5: your answer = 4, correct = 3%1$s" +
                            "Test passed, count of correct answers = 3.%1$s"
                    , System.lineSeparator());

    @DisplayName("должен корректно получать вывод.")
    @Test
    public void shouldGetCorrectOutput() {

        String INPUT = "Kirill\nKotikov\n4\n2\n4\n4\n4\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(INPUT.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StudentTestingServiceImpl studentServiceImpl = new StudentTestingServiceImpl(
                new IOServiceStreams(outputStream, inputStream), questionDaoImpl);
        studentServiceImpl.startTesting();
        assertEquals(OUTPUT, outputStream.toString(StandardCharsets.UTF_8));
    }
}
