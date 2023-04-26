package ru.kotikov.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kotikov.models.Question;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    public List<Question> getAll(String filePath) {
        List<Question> questions = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filePath)) {
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
