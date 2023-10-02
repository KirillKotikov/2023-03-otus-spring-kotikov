package ru.kotikov.docker.services;

import ru.kotikov.docker.dtos.BookDto;
import ru.kotikov.docker.dtos.BookWithCommentDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookWithCommentDto getBookWithCommentsByBookId(Long id);

}
