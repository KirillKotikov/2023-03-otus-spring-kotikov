package ru.kotikov;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.kotikov.services.AnswerReader;
import ru.kotikov.services.CsvReader;
import ru.kotikov.services.QuestionPrinter;
import ru.kotikov.services.ResultPrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        CsvReader csvReader = context.getBean(CsvReader.class);
        QuestionPrinter questionPrinter = context.getBean(QuestionPrinter.class);
        AnswerReader answerReader = context.getBean(AnswerReader.class);
        ResultPrinter resultPrinter = context.getBean(ResultPrinter.class);

        Map<String, Map<List<String>, String>> questions = csvReader.getQuestions("questions.csv");
        List<String> studentAndCorrectAnswers = new ArrayList<>();
        for (Map.Entry<String, Map<List<String>, String>> stringMapEntry : questions.entrySet()) {
            List<String> answers = stringMapEntry.getValue().keySet().stream().toList().get(0);
            questionPrinter.printQuestion(stringMapEntry.getKey(), answers);
            studentAndCorrectAnswers.add(answerReader.readAnswer());
            studentAndCorrectAnswers.add(stringMapEntry.getValue().get(answers));
        }
        resultPrinter.printResult(studentAndCorrectAnswers);
    }
}