package ru.kotikov.services;

public interface IOService {
    String readLine();

    static void print(String line) {
        System.out.print(line);
    }

    static void println(String line) {
        System.out.println(line);
    }
}
