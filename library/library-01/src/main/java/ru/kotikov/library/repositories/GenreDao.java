package ru.kotikov.library.repositories;

import ru.kotikov.library.models.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getAll();

    Genre getById(long id);
}
