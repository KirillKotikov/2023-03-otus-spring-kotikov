package ru.kotikov.dao;

import ru.kotikov.models.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getAll();
}
