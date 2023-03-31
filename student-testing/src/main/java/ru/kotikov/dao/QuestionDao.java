package ru.kotikov.dao;

import ru.kotikov.models.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDao {

    private final String csvTestFile;

    public QuestionDao(String csvTestFile) {
        this.csvTestFile = csvTestFile;
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        try (InputStream inputStream = QuestionDao.class.getClassLoader().getResourceAsStream(csvTestFile)) {
            assert inputStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while (reader.ready()) {
                    String[] splitLine = reader.readLine().split(";");
                    questions.add(new Question(Arrays.asList(splitLine[1].split(",")),
                            splitLine[2], splitLine[0]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
}
