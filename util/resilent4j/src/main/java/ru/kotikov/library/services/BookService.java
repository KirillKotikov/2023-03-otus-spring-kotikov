package ru.kotikov.library.services;

import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;

import java.util.List;
import java.util.Map;

public interface BookService {

    List<BookDto> getAllBooks();

    BookWithCommentDto getBookWithCommentsByBookId(String id);

    List<BookDto> getAllCache();

    Map<String, BookWithCommentDto> getByIdCache();

}
