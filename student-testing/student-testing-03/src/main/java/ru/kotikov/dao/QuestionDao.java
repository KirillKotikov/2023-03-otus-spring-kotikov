package ru.kotikov.dao;

import ru.kotikov.models.Question;
import ru.kotikov.services.MessageService;

import java.util.List;

public interface QuestionDao {

    MessageService getMessageService();

    List<Question> getAll();
}
