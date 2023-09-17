package ru.kotikov.library.controllers;

import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.library.Exceptions.ApiError;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.services.BookService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

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
    public CompletableFuture<BookWithCommentDto> getById(@PathVariable("id") String id) {
        return CompletableFuture.supplyAsync(() -> bookService.getBookWithCommentsByBookId(id));
    }

    public CompletableFuture<List<BookDto>> getAllBooksFallBack(Exception exception) {
        return CompletableFuture.supplyAsync(bookService::getAllCache);
    }

    public CompletableFuture<BookWithCommentDto> getBookWithCommentsByBookIdFallBack(
            String id, Exception exception) {
        BookWithCommentDto bookWithCommentDto = bookService.getByIdCache().get(id);
        if (bookWithCommentDto == null) {
            throw new DataNotFoundException(String.format(ExceptionMessages.BOOK_NOT_FOUND, id));
        } else {
            return CompletableFuture.supplyAsync(() -> bookWithCommentDto);
        }
    }

    @ExceptionHandler({TimeoutException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public CompletableFuture<Object> handleTimeoutException(Exception exception) {
        return CompletableFuture.supplyAsync(null);
    }

    @ExceptionHandler({DataNotFoundException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity<Object> handleDataNotFoundException(Exception exception) {
        ApiError apiErrorResponse = new ApiError(400, exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(apiErrorResponse);
    }

}