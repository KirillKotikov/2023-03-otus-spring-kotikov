package ru.kotikov.services;

import java.util.Scanner;

public class AnswerService implements IOService {
    private final Scanner scanner;

    public AnswerService() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        IOService.print("Enter an answer: ");
        return scanner.next();
    }
}
