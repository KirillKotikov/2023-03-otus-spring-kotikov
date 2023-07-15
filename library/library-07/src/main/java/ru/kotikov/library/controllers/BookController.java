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
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.repositories.BookRepository;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookRepository repository;

    @GetMapping("/api/book/")
    public Flux<BookDto> getAll() {
        return repository.findAll()
                .map(BookDto::toDto);
    }

    @GetMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getById(@PathVariable("id") String id) {
        return repository.findById(id)
                .map(BookDto::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));

    }

    @PostMapping("/api/book")
    public Mono<Book> saveBook(@Valid @RequestBody Book book) {
        return repository.save(book);
    }

    @PutMapping("/api/book")
    public Mono<Book> updateBook(@Valid @RequestBody Book book) {
        return repository.save(book);
    }

    @DeleteMapping("/api/book/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") String id) {
        return repository.deleteById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(
                        () -> ResponseEntity.notFound().build()));
    }

}