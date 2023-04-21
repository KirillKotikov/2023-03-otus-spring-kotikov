package ru.kotikov.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Question {
    private List<String> answers;

    private String correctAnswer;

    private String question;
}
