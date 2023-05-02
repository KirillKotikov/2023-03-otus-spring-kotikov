package ru.kotikov.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kotikov.models.Question;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Объект получающий вопросы из файла ")
@SpringBootTest
public class QuestionDaoImplTest {

    @Autowired
    private QuestionDaoImpl questionDaoImpl;

    @DisplayName("должен корректно получать вопросы из файла.")
    @Test
    public void shouldGetQuestions() {
        List<Question> questions = questionDaoImpl.getAll();
        Map<String, Map<List<String>, String>> mapQuestions = new HashMap<>();
        for (Question question : questions) {
            Map<List<String>, String> map = new HashMap<>();
            map.put(question.getAnswers(), question.getCorrectAnswer());
            mapQuestions.put(question.getQuestion(), map);
        }

        assertTrue(mapQuestions.containsKey("What is the latest version of Java?"));
        assertTrue(mapQuestions.containsKey("What is the maximum complexity of a boolean expression allowed?"));
        assertTrue(mapQuestions.containsKey("What is the style of writing a class name in Java?"));
        assertTrue(mapQuestions.containsKey("Select an example of a valid constant in Java."));
        assertTrue(mapQuestions.containsKey("If the value of the field does not change during the execution " +
                "of the program, then it should be?"));

        assertTrue(mapQuestions.get("What is the latest version of Java?")
                .containsKey(Arrays.asList("1) 17", "2) 23", "3) 99", "4) 20")));
        assertTrue(mapQuestions.get("What is the maximum complexity of a boolean expression allowed?")
                .containsKey(Arrays.asList("1) 2", "2) 4", "3) 64", "4) 8")));
        assertTrue(mapQuestions.get("What is the style of writing a class name in Java?")
                .containsKey(Arrays.asList("1) CamelCase", "2) lowerCamelCase", "3) snake_case", "4) dog@case")));
        assertTrue(mapQuestions.get("Select an example of a valid constant in Java.")
                .containsKey(Arrays.asList("1) initial Const const", "2) String CONST", "3) static final String const",
                        "4) static final String CONST")));
        assertTrue(mapQuestions.get("If the value of the field does not change during the execution " +
                        "of the program, then it should be?")
                .containsKey(Arrays.asList("1) public", "2) static", "3) final", "4) Destroyed! :)")));

        assertEquals(mapQuestions.get("What is the latest version of Java?")
                .get(Arrays.asList("1) 17", "2) 23", "3) 99", "4) 20")), "4");
        assertEquals(mapQuestions.get("What is the maximum complexity of a boolean expression allowed?")
                .get(Arrays.asList("1) 2", "2) 4", "3) 64", "4) 8")), "2");
        assertEquals(mapQuestions.get("What is the style of writing a class name in Java?")
                .get(Arrays.asList("1) CamelCase", "2) lowerCamelCase", "3) snake_case", "4) dog@case")), "1");
        assertEquals(mapQuestions.get("Select an example of a valid constant in Java.")
                .get(Arrays.asList("1) initial Const const", "2) String CONST", "3) static final String const",
                        "4) static final String CONST")), "4");
        assertEquals(mapQuestions.get("If the value of the field does not change during the execution " +
                        "of the program, then it should be?")
                .get(Arrays.asList("1) public", "2) static", "3) final", "4) Destroyed! :)")), "3");
    }
}
