package ru.kotikov.services;

import java.util.List;

public class ResultPrinterImpl implements ResultPrinter {

    @Override
    public void printResult(List<String> result) {
        System.out.println("Results:");
        byte answerCount = 1;
        for (int i = 0; i < result.size(); i += 2) {
            System.out.println("Question №" + answerCount + ": your answer = "
                    + result.get(i) + ", correct = " + result.get(i + 1));
            answerCount++;
        }
    }
}
