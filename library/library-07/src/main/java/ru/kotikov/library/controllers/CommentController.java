package ru.kotikov.library.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.repositories.CommentRepository;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository repository;

    @GetMapping("/api/comment/")
    public Flux<CommentDto> getAllByBook(@Valid @RequestBody Book book) {
        return repository.findByBook(book)
                .map(CommentDto::toDto);
    }

    @GetMapping("/api/comment/{bookId}")
    public Flux<ResponseEntity<CommentDto>> getById(@PathVariable("bookId") String bookId) {
        return repository.findByBookId(bookId)
                .map(CommentDto::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));

    }

    @PostMapping("/api/comment")
    public Mono<Comment> saveComment(@Valid @RequestBody Comment comment) {
        return repository.save(comment);
    }

    @PutMapping("/api/comment")
    public Mono<Comment> updateComment(@Valid @RequestBody Comment comment) {
        return repository.save(comment);
    }

    @DeleteMapping("/api/comment/{bookId}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable("bookId") String bookId) {
        return repository.deleteByBookId(bookId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));
    }

}
