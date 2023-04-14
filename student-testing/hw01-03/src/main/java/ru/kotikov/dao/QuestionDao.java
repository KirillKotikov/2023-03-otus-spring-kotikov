package ru.kotikov.dao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.kotikov.configs.AppProps;
import ru.kotikov.models.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Getter
public class QuestionDao {

    private final AppProps props;

    private final MessageSource messageSource;

    private final int minCorrectAnswers;

    public QuestionDao(AppProps props, MessageSource messageSource,
                       @Value("${questionDao.minCorrectAnswers}") int minCorrectAnswers) {
        this.props = props;
        this.messageSource = messageSource;
        this.minCorrectAnswers = minCorrectAnswers;
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        String key = "question.";
        for (int i = 1; i < 6; i++) {
            String message = messageSource.getMessage(key + i, null, props.getLocale());
            String[] splitMessage = message.split(";");
            questions.add(new Question(Arrays.asList(splitMessage[1].split(",")),
                    splitMessage[2], splitMessage[0]));
        }
        return questions;
    }
}
