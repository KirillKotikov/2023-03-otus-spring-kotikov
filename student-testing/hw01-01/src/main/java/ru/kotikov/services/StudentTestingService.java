package ru.kotikov.services;

import org.springframework.stereotype.Service;
import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;
import ru.kotikov.models.Student;

import java.util.ArrayList;
import java.util.List;

@Service
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
        Student student = getStudent();
        List<Question> questions = questionDao.getQuestions();
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        getStudentAnswers(questions, studentAndCorrectAnswers);
        resultPrinter.printResult(studentAndCorrectAnswers, student);
        checkAnswers(studentAndCorrectAnswers);
    }


    private Student getStudent() {
        iOService.printLine("Enter your first name: ");
        String studentFirstName = iOService.readLine();
        iOService.printLine("Enter your last name: ");
        String studentLastName = iOService.readLine();
        return new Student(studentFirstName, studentLastName);
    }

    private void getStudentAnswers(List<Question> questions, List<String> studentAndCorrectAnswers) {
        for (Question question : questions) {
            iOService.printLine(question.getQuestion() + "\n");
            for (String answer : question.getAnswers()) {
                iOService.printLine(answer + "\n");
            }
            iOService.printLine("Enter an answer: ");
            studentAndCorrectAnswers.add(iOService.readLine());
            studentAndCorrectAnswers.add(question.getCorrectAnswer());
        }
    }

    public boolean checkAnswers(List<String> studentAndCorrectAnswers) {
        byte correctAnswersCount = 0;
        for (int i = 0; i < studentAndCorrectAnswers.size() - 1; i += 2) {
            if (studentAndCorrectAnswers.get(i).equals(studentAndCorrectAnswers.get(i + 1))) {
                correctAnswersCount++;
            }
        }
        if (correctAnswersCount >= questionDao.getMinCorrectAnswers()) {
            iOService.printLine("Test passed, count of correct answers = " + correctAnswersCount + ".");
            return true;
        } else {
            iOService.printLine("Test failed, count of correct answers = " + correctAnswersCount + ". " +
                    "Min count of correct answers = " + questionDao.getMinCorrectAnswers() + ".");
            return false;
        }
    }
}
