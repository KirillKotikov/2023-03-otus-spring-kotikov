package ru.kotikov.library.repositories;


import ru.kotikov.library.models.Book;

import java.util.List;

public interface BookDao {
    int count();

    List<Book> getAll();

    Book insert(Book book);

    Book getById(long id);

    Book update(Book book);

    int deleteById(long id);
}
