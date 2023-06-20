package ru.kotikov.library.services;

import ru.kotikov.library.models.Book;

import java.util.List;

public interface BookService {
    long countAllBooks();

    List<Book> getAllBooks();

    Book addBook(String name, String authorId, String genreId);

    Book getBookById(String id);

    Book updateBook(String id, String name, String authorId, String genreId);

    void deleteBookById(String id);
}
