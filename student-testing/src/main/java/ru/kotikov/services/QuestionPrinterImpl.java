package ru.kotikov.services;

import java.util.List;

public class QuestionPrinterImpl implements QuestionPrinter {

    @Override
    public void printQuestion(String question, List<String> answers) {
        IOService.println(question);
        for (String answer : answers) {
            IOService.println(answer);
        }
    }
}
