package ru.kotikov.services;

import java.util.List;

public class QuestionPrinterImpl implements QuestionPrinter {

    @Override
    public void printQuestion(String question, List<String> answers) {
        System.out.println(question);
        for (String answer : answers) {
            System.out.println(answer);
        }
    }
}
