package ru.kotikov.library.repositories;

import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAll();

    List<Comment> findByBook(Book book);

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    int deleteById(long id);
}
