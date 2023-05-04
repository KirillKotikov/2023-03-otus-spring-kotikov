package ru.kotikov.library.services;

import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.models.Book;

import java.util.List;

public interface BookService {
    int countAllBooks();

    List<Book> getAllBooks();

    Book addBook(String name, long authorId, long genreId) throws DataNotFoundException;

    Book getBookById(long id) throws DataNotFoundException;

    Book updateBook(long id, String name, Long authorId, Long genreId)
            throws DataNotFoundException;

    int deleteBookById(long id);
}
