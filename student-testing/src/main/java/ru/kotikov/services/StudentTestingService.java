package ru.kotikov.services;

import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;

import java.util.ArrayList;
import java.util.List;

public class StudentTestingService {
    private final IOService iOService;

    private final ResultPrinter resultPrinter;

    private final QuestionDao questionDao;

    public StudentTestingService(IOService iOService, ResultPrinter resultPrinter,
                                 QuestionDao questionDao) {
        this.iOService = iOService;
        this.resultPrinter = resultPrinter;
        this.questionDao = questionDao;
    }

    public void startTesting() {

        List<Question> questions = questionDao.getQuestions();
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        for (Question question : questions) {
            iOService.printLine(question.getQuestion() + "\n");
            for (String answer : question.getAnswers()) {
                iOService.printLine(answer + "\n");
            }
            studentAndCorrectAnswers.add(iOService.readLine());
            studentAndCorrectAnswers.add(question.getCorrectAnswer());
        }
        resultPrinter.printResult(studentAndCorrectAnswers);
    }
}
