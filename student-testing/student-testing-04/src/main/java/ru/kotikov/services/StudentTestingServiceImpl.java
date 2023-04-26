package ru.kotikov.services;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.kotikov.configs.AppProps;
import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;
import ru.kotikov.models.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentTestingServiceImpl implements StudentTestingService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    private final MessageSource messageSource;

    private final AppProps appProps;


    public StudentTestingServiceImpl(IOServiceStreams ioService, QuestionDao questionDao,
                                     MessageSource messageSource, AppProps appProps) {
        this.ioService = ioService;
        this.questionDao = questionDao;
        this.messageSource = messageSource;
        this.appProps = appProps;
    }

    public void startTesting(Student student) {
        List<Question> questions = questionDao.getAll(appProps.getQuestionsFilePath());
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
                    messageSource.getMessage("answer", null, appProps.getLocale())));
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
        int minCorrectAnswers = appProps.getMinCorrectAnswers();
        if (correctAnswersCount >= minCorrectAnswers) {
            ioService.printLine(String.format(messageSource.getMessage("testPassed", null,
                    appProps.getLocale()), correctAnswersCount));

        } else {
            ioService.printLine(String.format(messageSource.getMessage("testFailed", null,
                    appProps.getLocale()), correctAnswersCount, minCorrectAnswers));
        }
    }

    private void printResult(List<String> result, Student student) {
        ioService.printLine(String.format(messageSource.getMessage("testResults", null,
                appProps.getLocale()), student.getFirstName(), student.getLastName()));
        int answerCount = 1;
        for (int i = 0; i < result.size(); i += 2) {
            ioService.printLine(String.format(messageSource.getMessage("questionResult", null,
                    appProps.getLocale()), answerCount, result.get(i), result.get(i + 1)));
            answerCount++;
        }
    }

}
