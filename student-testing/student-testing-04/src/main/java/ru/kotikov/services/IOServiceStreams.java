package ru.kotikov.services;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class IOServiceStreams implements IOService {
    private final PrintStream output;

    private final Scanner input;

    public IOServiceStreams() {
        output = new PrintStream(System.out);
        input = new Scanner(System.in);
    }

    public IOServiceStreams(OutputStream outputStream, InputStream inputStream) {
        output = new PrintStream(outputStream);
        input = new Scanner(inputStream);
    }

    @Override
    public void printLine(String s) {
        output.println(s);
    }

    @Override
    public String readLineWithPrompt(String prompt) {
        printLine(prompt);
        return input.nextLine();
    }
}
