package ru.kotikov.library.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.services.AuthorService;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.CommentService;
import ru.kotikov.library.services.GenreService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookRestController {

    private final BookService bookService;

    private final CommentService commentService;

    private final AuthorService authorService;

    private final GenreService genreService;

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

    @DeleteMapping("/api/book/{id}")
    public void deleteBook(@PathVariable("id") String id) {
        bookService.deleteBookById(id);
    }

    @DeleteMapping("/api/book/{bookId}/comment/{commentId}")
    public void deleteComment(@PathVariable(value = "commentId") String commentId,
                              @PathVariable(value = "bookId") String bookId,
                              HttpServletResponse response) {
        commentService.deleteById(commentId);
        try {
            response.sendRedirect("/api/book");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/api/book/{bookId}/comment")
    public CommentDto saveComment(@Valid @RequestBody CommentDto comment) {
        return commentService.addComment(comment);
    }
}