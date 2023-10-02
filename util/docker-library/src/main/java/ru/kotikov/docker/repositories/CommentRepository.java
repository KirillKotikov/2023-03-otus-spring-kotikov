package ru.kotikov.docker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.docker.models.Book;
import ru.kotikov.docker.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);

}
