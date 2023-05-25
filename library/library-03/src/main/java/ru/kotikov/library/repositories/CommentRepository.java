package ru.kotikov.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);

    Optional<Comment> findById(long id);
}
