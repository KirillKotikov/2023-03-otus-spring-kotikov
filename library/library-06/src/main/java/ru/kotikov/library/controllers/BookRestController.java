package ru.kotikov.library.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    @GetMapping("/api/book/")
    public List<BookDto> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/book/{id}")
    public BookWithCommentDto getById(@PathVariable("id") String id) {
        return bookService.getBookWithCommentsByBookId(id);
    }

    @PostMapping("/api/book")
    public BookDto saveBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.saveBook(bookDto);
    }

    @PutMapping("/api/book")
    public BookDto updateBook(@Valid @RequestBody BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        bookService.deleteBookById(id);
    }

}