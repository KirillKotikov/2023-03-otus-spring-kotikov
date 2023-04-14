package ru.kotikov.services;

import org.springframework.stereotype.Service;
import ru.kotikov.dao.QuestionDao;
import ru.kotikov.models.Question;
import ru.kotikov.models.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentTestingServiceImpl implements StudentTestingService {
    private final IOServiceStreams ioServiceStreams;

    private final QuestionDao questionDao;

    public StudentTestingServiceImpl(IOServiceStreams ioServiceStreams, QuestionDao questionDao) {
        this.ioServiceStreams = ioServiceStreams;
        this.questionDao = questionDao;
    }

    public void startTesting() {
        Student student = getStudent();
        List<Question> questions = questionDao.getQuestions();
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        getStudentAnswers(questions, studentAndCorrectAnswers);
        printResult(studentAndCorrectAnswers, student);
        checkAnswers(studentAndCorrectAnswers);
    }


    private Student getStudent() {
        String studentFirstName = ioServiceStreams.readLineWithPrompt("Enter your first name: ");
        String studentLastName = ioServiceStreams.readLineWithPrompt("Enter your last name: ");
        return new Student(studentFirstName, studentLastName);
    }

    private void getStudentAnswers(List<Question> questions, List<String> studentAndCorrectAnswers) {
        for (Question question : questions) {
            ioServiceStreams.printLine(question.getQuestion());
            for (String answer : question.getAnswers()) {
                ioServiceStreams.printLine(answer);
            }
            studentAndCorrectAnswers.add(ioServiceStreams.readLineWithPrompt("Enter an answer: "));
            studentAndCorrectAnswers.add(question.getCorrectAnswer());
        }
    }

    private void checkAnswers(List<String> studentAndCorrectAnswers) {
        byte correctAnswersCount = 0;
        for (int i = 0; i < studentAndCorrectAnswers.size() - 1; i += 2) {
            if (studentAndCorrectAnswers.get(i).equals(studentAndCorrectAnswers.get(i + 1))) {
                correctAnswersCount++;
            }
        }
        if (correctAnswersCount >= questionDao.getMinCorrectAnswers()) {
            ioServiceStreams.printLine("Test passed, count of correct answers = " + correctAnswersCount + ".");
        } else {
            ioServiceStreams.printLine("Test failed, count of correct answers = " + correctAnswersCount + ". " +
                    "Min count of correct answers = " + questionDao.getMinCorrectAnswers() + ".");
        }
    }

    private void printResult(List<String> result, Student student) {
        ioServiceStreams.printLine(String.format("Test results of student %s %s:",
                student.getFirstName(), student.getLastName()));
        int answerCount = 1;
        for (int i = 0; i < result.size(); i += 2) {
            ioServiceStreams.printLine("Question №" + answerCount + ": your answer = "
                    + result.get(i) + ", correct = " + result.get(i + 1));
            answerCount++;
        }
    }

}
