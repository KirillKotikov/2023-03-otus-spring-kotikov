package ru.kotikov.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.kotikov.dao.QuestionDao;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTestingServiceTest {

    @Mock
    private IOService iOService = new AnswerService();

    @Mock
    private ResultPrinter resultPrinter;

    private final QuestionDao questionDao = new QuestionDao("questions.csv", (byte) 3);

    private final StudentTestingService studentTestingService = new StudentTestingService(
            iOService, resultPrinter, questionDao
    );

    @Test
    public void testShouldBeFiled() {
        assertFalse(studentTestingService.checkAnswers(List.of("1", "2", "2", "2", "3", "3")));
        assertFalse(studentTestingService.checkAnswers(List.of("1", "1", "2", "3", "3", "4", "2", "2", "6", "3")));
    }

    @Test
    public void testShouldBePassed() {
        assertTrue(studentTestingService.checkAnswers(List.of("1", "1", "2", "2", "3", "3")));
        assertTrue(studentTestingService.checkAnswers(List.of("1", "1", "2", "3", "3", "4", "2", "2", "3", "3")));
    }
}
