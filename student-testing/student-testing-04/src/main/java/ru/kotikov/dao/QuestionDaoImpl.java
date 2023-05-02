package ru.kotikov.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kotikov.models.Question;
import ru.kotikov.providers.ResourceProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {

    private final ResourceProvider resourceProvider;

    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();
        try (InputStream inputStream =
                     QuestionDaoImpl.class.getClassLoader()
                             .getResourceAsStream(resourceProvider.getQuestionsFilePath())) {
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
