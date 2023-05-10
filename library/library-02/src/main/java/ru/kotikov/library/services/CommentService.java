package ru.kotikov.library.services;

import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    List<Comment> getByBook(Book book);

    Comment addComment(Comment comment);

    Comment updateComment(Comment comment);

    Comment getById(long id);

    int deleteById(long id);
}
