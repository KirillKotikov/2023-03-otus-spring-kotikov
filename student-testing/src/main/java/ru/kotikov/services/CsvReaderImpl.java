package ru.kotikov.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReaderImpl implements CsvReader {

    @Override
    public Map<String, Map<List<String>, String>> getQuestions(String resourceName) {
        Map<String, Map<List<String>, String>> result = new HashMap<>();
        try (InputStream inputStream = CsvReaderImpl.class.getClassLoader().getResourceAsStream(resourceName)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while (reader.ready()) {
                    String[] splitLine = reader.readLine().split(";");
                    Map<List<String>, String> answers = new HashMap<>();
                    answers.put(Arrays.asList(splitLine[1].split(",")), splitLine[2]);
                    result.put(splitLine[0], answers);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
