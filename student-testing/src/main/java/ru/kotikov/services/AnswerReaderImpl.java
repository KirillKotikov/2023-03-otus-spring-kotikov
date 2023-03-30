package ru.kotikov.services;

import java.util.Scanner;

public class AnswerReaderImpl implements AnswerReader {

    @Override
    public String readAnswer() {
        System.out.print("Enter an answer: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
