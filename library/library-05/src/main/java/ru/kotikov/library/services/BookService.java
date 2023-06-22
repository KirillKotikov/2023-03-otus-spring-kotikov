package ru.kotikov.library.services;

import ru.kotikov.library.dtos.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDto getBookById(String id);

    BookDto saveBook(String id, String name, String authorId, String genreId);

    void deleteBookById(String id);
}
