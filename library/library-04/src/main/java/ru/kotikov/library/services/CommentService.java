package ru.kotikov.library.services;

import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getByBook(Book book);

    Comment addComment(Comment comment);

    Comment updateCommentText(String commentId, String text);

    Comment getById(String id);

    void deleteById(String id);
}
