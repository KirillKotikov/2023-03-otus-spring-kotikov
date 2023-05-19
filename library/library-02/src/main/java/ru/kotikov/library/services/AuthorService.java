package ru.kotikov.library.services;

import ru.kotikov.library.models.Author;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
}
