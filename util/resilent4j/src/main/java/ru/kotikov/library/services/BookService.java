package ru.kotikov.library.services;

import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookWithCommentDto getBookWithCommentsByBookId(String id);

}
