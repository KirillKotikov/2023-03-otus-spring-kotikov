package ru.kotikov.services;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AnswerService implements IOService {
    private final Scanner scanner;

    public AnswerService() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String readLine() {
        return scanner.next();
    }

    @Override
    public void printLine(String line) {
        System.out.print(line);
    }
}
