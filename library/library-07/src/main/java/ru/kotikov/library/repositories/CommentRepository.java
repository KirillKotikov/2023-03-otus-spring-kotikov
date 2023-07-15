package ru.kotikov.library.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {
    Flux<Comment> findByBook(Book book);

    Flux<Comment> findByBookId(String bookId);

    Mono<Comment> findById(String id);

    Mono<Void> deleteByBookId(String id);
}
