package ru.kotikov.library.repositories;

import ru.kotikov.library.models.Author;

import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getById(long id);
}
