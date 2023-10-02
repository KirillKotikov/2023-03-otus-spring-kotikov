package ru.kotikov.docker.controllers;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.docker.dtos.BookDto;
import ru.kotikov.docker.dtos.BookWithCommentDto;
import ru.kotikov.docker.services.BookService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/book/")
    @TimeLimiter(name = "getAll", fallbackMethod = "getAllBooksFallBack")
    public CompletableFuture<List<BookDto>> getAll() {
        return CompletableFuture.supplyAsync(bookService::getAllBooks);
    }

    @GetMapping("/api/book/{id}")
    @TimeLimiter(name = "getById", fallbackMethod = "getBookWithCommentsByBookIdFallBack")
    public CompletableFuture<BookWithCommentDto> getById(@PathVariable("id") Long id) {
        return CompletableFuture.supplyAsync(() -> bookService.getBookWithCommentsByBookId(id));
    }

}