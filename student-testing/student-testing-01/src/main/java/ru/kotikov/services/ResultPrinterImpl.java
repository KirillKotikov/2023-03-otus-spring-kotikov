package ru.kotikov.services;

import org.springframework.stereotype.Component;
import ru.kotikov.models.Student;

import java.util.List;

@Component
public class ResultPrinterImpl implements ResultPrinter {

    @Override
    public void printResult(List<String> result, Student student) {
        System.out.printf("Test results of student %s %s:%n",
                student.getFirstName(), student.getLastName());
        byte answerCount = 1;
        for (int i = 0; i < result.size(); i += 2) {
            System.out.println("Question №" + answerCount + ": your answer = "
                    + result.get(i) + ", correct = " + result.get(i + 1));
            answerCount++;
        }
    }
}
