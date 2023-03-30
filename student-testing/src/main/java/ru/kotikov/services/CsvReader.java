package ru.kotikov.services;

import java.util.List;
import java.util.Map;

public interface CsvReader {
    Map<String, Map<List<String>, String>> getQuestions(String resourceName);
}
