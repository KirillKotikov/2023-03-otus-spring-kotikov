package ru.kotikov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;
import ru.kotikov.models.Student;
import ru.kotikov.providers.QuestionParamsProvider;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentTestingServiceImpl implements StudentTestingService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    private final MessageLocaleService messageLocaleService;

    private final QuestionParamsProvider questionParamsProvider;

    public void startTesting(Student student) {
        List<Question> questions = questionDao.getAll();
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        getStudentAnswers(questions, studentAndCorrectAnswers);
        printResult(studentAndCorrectAnswers, student);
        checkAnswers(studentAndCorrectAnswers);
    }

    private void getStudentAnswers(List<Question> questions, List<String> studentAndCorrectAnswers) {
        for (Question question : questions) {
            ioService.printLine(question.getQuestion());
            for (String answer : question.getAnswers()) {
                ioService.printLine(answer);
            }
            studentAndCorrectAnswers.add(ioService.readLineWithPrompt(
                    messageLocaleService.getMessageSource().getMessage("answer", null,
                            messageLocaleService.getLocaleProvider().getLocale())));
            studentAndCorrectAnswers.add(question.getCorrectAnswer());
        }
    }

    private void checkAnswers(List<String> studentAndCorrectAnswers) {
        int correctAnswersCount = 0;
        for (int i = 0; i < studentAndCorrectAnswers.size() - 1; i += 2) {
            if (studentAndCorrectAnswers.get(i).equals(studentAndCorrectAnswers.get(i + 1))) {
                correctAnswersCount++;
            }
        }
        int minCorrectAnswers = questionParamsProvider.getMinCorrectAnswers();
        if (correctAnswersCount >= minCorrectAnswers) {
            ioService.printLine(String.format(messageLocaleService.getMessageSource().getMessage("testPassed",
                    null, messageLocaleService.getLocaleProvider().getLocale()), correctAnswersCount));

        } else {
            ioService.printLine(String.format(messageLocaleService.getMessageSource().getMessage("testFailed", null,
                    messageLocaleService.getLocaleProvider().getLocale()), correctAnswersCount, minCorrectAnswers));
        }
    }

    private void printResult(List<String> result, Student student) {
        ioService.printLine(String.format(messageLocaleService.getMessageSource().getMessage("testResults", null,
                messageLocaleService.getLocaleProvider().getLocale()), student.getFirstName(), student.getLastName()));
        int answerCount = 1;
        for (int i = 0; i < result.size(); i += 2) {
            ioService.printLine(String.format(messageLocaleService.getMessageSource().getMessage("questionResult",
                            null, messageLocaleService.getLocaleProvider().getLocale()),
                    answerCount, result.get(i), result.get(i + 1)));
            answerCount++;
        }
    }

}
