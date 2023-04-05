package ru.kotikov.services;

import ru.kotikov.models.Student;

import java.util.List;

public interface ResultPrinter {

    void printResult(List<String> result, Student student);
}
