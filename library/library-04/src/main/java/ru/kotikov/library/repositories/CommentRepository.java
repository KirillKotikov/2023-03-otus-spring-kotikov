package ru.kotikov.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBook(Book book);

    Optional<Comment> findById(String id);
}
