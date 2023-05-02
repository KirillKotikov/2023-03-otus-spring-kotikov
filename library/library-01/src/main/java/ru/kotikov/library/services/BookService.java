package ru.kotikov.library.services;

public interface BookService {
    String countAllBooks();

    String getAllBooks();

    String addBook(String name, long authorId, long genreId);

    String getBookById(long id);

    String updateBook(long id, String name, Long authorId, Long genreId);

    String deleteBookById(long id);
}
