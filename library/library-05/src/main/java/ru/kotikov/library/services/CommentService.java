package ru.kotikov.library.services;

import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.models.Book;

import java.util.List;

public interface CommentService {

    List<CommentDto> getByBook(Book book);

    List<CommentDto> getByBookId(String bookId);

    CommentDto addComment(String bookId, String text);

    CommentDto updateCommentText(String commentId, String text);

    CommentDto getById(String id);

    void deleteById(String id);
}
