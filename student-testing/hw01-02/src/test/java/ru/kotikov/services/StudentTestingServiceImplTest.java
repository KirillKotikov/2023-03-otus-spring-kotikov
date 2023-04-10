package ru.kotikov.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.kotikov.dao.QuestionDao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTestingServiceImplTest {

    @Mock
    private QuestionDao questionDao = new QuestionDao("questions.csv", 3);


    private final String INPUT = "Kirill\nKotikov\n4\n2\n4\n4\n4\n";
    private final String OUTPUT =
            """
                    Enter your first name: \r
                    Enter your last name: \r
                    What is the latest version of Java?\r
                    1) 17\r
                    2) 23\r
                    3) 99\r
                    4) 20\r
                    Enter an answer: \r
                    What is the maximum complexity of a boolean expression allowed?\r
                    1) 2\r
                    2) 4\r
                    3) 64\r
                    4) 8\r
                    Enter an answer: \r
                    What is the style of writing a class name in Java?\r
                    1) CamelCase\r
                    2) lowerCamelCase\r
                    3) snake_case\r
                    4) dog@case\r
                    Enter an answer: \r
                    Select an example of a valid constant in Java.\r
                    1) initial Const const\r
                    2) String CONST\r
                    3) static final String const\r
                    4) static final String CONST\r
                    Enter an answer: \r
                    If the value of the field does not change during the execution of the program, then it should be?\r
                    1) public\r
                    2) static\r
                    3) final\r
                    4) Destroyed! :)\r
                    Enter an answer: \r
                    Test results of student Kirill Kotikov:\r
                    Question №1: your answer = 4, correct = 4\r
                    Question №2: your answer = 2, correct = 2\r
                    Question №3: your answer = 4, correct = 1\r
                    Question №4: your answer = 4, correct = 4\r
                    Question №5: your answer = 4, correct = 3\r
                    Test passed, count of correct answers = 3.\r
                    """;

    @Test
    public void shouldGetCorrectOutput() {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(INPUT.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StudentTestingServiceImpl studentServiceImpl = new StudentTestingServiceImpl(
                new IOServiceStreams(outputStream, inputStream), questionDao);
        studentServiceImpl.startTesting();
        assertEquals(outputStream.toString(StandardCharsets.UTF_8), OUTPUT);
    }
}
