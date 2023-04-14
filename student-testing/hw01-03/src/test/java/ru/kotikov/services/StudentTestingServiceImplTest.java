package ru.kotikov.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import ru.kotikov.configs.AppProps;
import ru.kotikov.dao.QuestionDao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StudentTestingServiceImplTest {


    private QuestionDao questionDao;

    private final AppProps appProps = mock(AppProps.class);

    private final MessageSource messageSource = mock(MessageSource.class);


    @BeforeEach
    public void initialize() {
        when(appProps.getLocale()).thenReturn(Locale.ENGLISH);
        when(messageSource.getMessage("question.1", null, Locale.ENGLISH)).thenReturn("What is the latest version of Java?;1) 17,2) 23,3) 99,4) 20;4");
        when(messageSource.getMessage("question.2", null, Locale.ENGLISH)).thenReturn("What is the maximum complexity of a boolean expression allowed?;1) 2,2) 4,3) 64,4) 8;2");
        when(messageSource.getMessage("question.3", null, Locale.ENGLISH)).thenReturn("What is the style of writing a class name in Java?;1) CamelCase,2) lowerCamelCase,3) snake_case,4) dog@case;1");
        when(messageSource.getMessage("question.4", null, Locale.ENGLISH)).thenReturn("Select an example of a valid constant in Java.;1) initial Const const,2) String CONST,3) static final String const,4) static final String CONST;4");
        when(messageSource.getMessage("question.5", null, Locale.ENGLISH)).thenReturn("If the value of the field does not change during the execution of the program, then it should be?;1) public,2) static,3) final,4) Destroyed! :);3");
        questionDao = new QuestionDao(appProps, messageSource, 3);
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

    @Test
    public void shouldGetCorrectOutput() {

        String INPUT = "Kirill\nKotikov\n4\n2\n4\n4\n4\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(INPUT.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StudentTestingServiceImpl studentServiceImpl = new StudentTestingServiceImpl(
                new IOServiceStreams(outputStream, inputStream), questionDao);
        studentServiceImpl.startTesting();
        assertEquals(outputStream.toString(StandardCharsets.UTF_8), OUTPUT);
    }
}
