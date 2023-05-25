package ru.kotikov.library.services;

import ru.kotikov.library.models.Book;

import java.util.List;

public interface BookService {
    long countAllBooks();

    List<Book> getAllBooks();

    Book addBook(String name, long authorId, long genreId);

    Book getBookById(long id);

    Book updateBook(long id, String name, Long authorId, Long genreId);

    void deleteBookById(long id);
}
