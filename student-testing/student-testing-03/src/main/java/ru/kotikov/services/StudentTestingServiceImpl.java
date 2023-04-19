package ru.kotikov.services;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;
import ru.kotikov.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class StudentTestingServiceImpl implements StudentTestingService {
    private final IOService ioService;

    private final QuestionDao questionDao;

    private final MessageSource messageSource;

    private final Locale locale;


    public StudentTestingServiceImpl(IOServiceStreams ioService, QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
        this.messageSource = questionDao.getMessageService().getMessageSource();
        this.locale = questionDao.getMessageService().getLocaleProvider().getLocale();
    }

    public void startTesting() {
        Student student = getStudent();
        List<Question> questions = questionDao.getAll();
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        getStudentAnswers(questions, studentAndCorrectAnswers);
        printResult(studentAndCorrectAnswers, student);
        checkAnswers(studentAndCorrectAnswers);
    }


    private Student getStudent() {
        String studentFirstName = ioService.readLineWithPrompt(
                messageSource.getMessage("getFirstName", null, locale));
        String studentLastName = ioService.readLineWithPrompt(
                messageSource.getMessage("getLastName", null, locale));
        return new Student(studentFirstName, studentLastName);
    }

    private void getStudentAnswers(List<Question> questions, List<String> studentAndCorrectAnswers) {
        for (Question question : questions) {
            ioService.printLine(question.getQuestion());
            for (String answer : question.getAnswers()) {
                ioService.printLine(answer);
            }
            studentAndCorrectAnswers.add(ioService.readLineWithPrompt(
                    messageSource.getMessage("answer", null, locale)));
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
        int minCorrectAnswers = questionDao.getMessageService().getQuestionParamsProvider().getMinCorrectAnswers();
        if (correctAnswersCount >= minCorrectAnswers) {
            ioService.printLine(String.format(messageSource.getMessage("testPassed", null, locale),
                    correctAnswersCount));

        } else {
            ioService.printLine(String.format(messageSource.getMessage("testFailed", null, locale),
                    correctAnswersCount, minCorrectAnswers));
        }
    }

    private void printResult(List<String> result, Student student) {
        ioService.printLine(String.format(messageSource.getMessage("testResults", null, locale),
                student.getFirstName(), student.getLastName()));
        int answerCount = 1;
        for (int i = 0; i < result.size(); i += 2) {
            ioService.printLine(String.format(messageSource.getMessage("questionResult", null, locale),
                            answerCount, result.get(i), result.get(i + 1)));
            answerCount++;
        }
    }

}
