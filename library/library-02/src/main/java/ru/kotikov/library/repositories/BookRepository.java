package ru.kotikov.library.repositories;


import ru.kotikov.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();

    List<Book> findAll();

    Book save(Book book);

    Optional<Book> findById(long id);

    void deleteById(long id);
}
