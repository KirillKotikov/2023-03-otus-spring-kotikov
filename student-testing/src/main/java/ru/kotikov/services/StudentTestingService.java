package ru.kotikov.services;

import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;

import java.util.ArrayList;
import java.util.List;

public class StudentTestingService {
    private final String csvTestFile;

    private final IOService iOService;

    private final ResultPrinter resultPrinter;

    private final QuestionDao questionDao;

    private final QuestionPrinter questionPrinter;

    public StudentTestingService(String csvTestFile, IOService iOService, ResultPrinter resultPrinter,
                                 QuestionDao questionDao, QuestionPrinter questionPrinter) {
        this.csvTestFile = csvTestFile;
        this.iOService = iOService;
        this.resultPrinter = resultPrinter;
        this.questionDao = questionDao;
        this.questionPrinter = questionPrinter;
    }

    public void startTesting() {

        List<Question> questions = questionDao.getQuestions(csvTestFile);
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        for (Question question : questions) {
            questionPrinter.printQuestion(question.getQuestion(), question.getAnswers());
            studentAndCorrectAnswers.add(iOService.readLine());
            studentAndCorrectAnswers.add(question.getCorrectAnswer());
        }
        resultPrinter.printResult(studentAndCorrectAnswers);
    }
}
