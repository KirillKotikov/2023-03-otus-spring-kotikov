package ru.kotikov.library.services;

import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookWithCommentDto getBookWithCommentsByBookId(String id);

    BookDto getBookById(String id);

    BookDto saveBook(BookDto bookDto);

    @Transactional
    BookDto updateBook(BookDto bookDto);

    void deleteBookById(String id);
}
