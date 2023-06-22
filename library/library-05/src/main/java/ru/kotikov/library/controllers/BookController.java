package ru.kotikov.library.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.CommentDto;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final CommentService commentService;

    @GetMapping("/api/book")
    public String getAll(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "allBooks";
    }

    @GetMapping("/api/book/")
    public String getById(@Valid @ModelAttribute("id") String id, Model model) {
        BookDto book = bookService.getBookById(id);
        List<CommentDto> comments = commentService.getByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "showBook";
    }

    @GetMapping("/api/book/addingPage")
    public String addingPage() {
        return "addingPage";
    }

    @GetMapping("/api/book/editPage/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "editPage";
    }

    @PostMapping("/api/book")
    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto) {
        bookService.saveBook(bookDto.getId(), bookDto.getName(), bookDto.getAuthorId(), bookDto.getGenreId());
        return "redirect:/api/book";
    }

    @PostMapping("/api/book/delete") // как я понял thymeleaf поддерживает только get и post запросы
    public String deleteBook(@Valid @ModelAttribute("id") String id) {
        bookService.deleteBookById(id);
        return "redirect:/api/book";
    }

    @PostMapping("/api/book/{bookId}/comment/delete")
    public String deleteComment(@RequestParam(value = "commentId") String commentId,
                                @PathVariable(value = "bookId") String bookId, Model model) {
        commentService.deleteById(commentId);
        List<CommentDto> comments = commentService.getByBookId(bookId);
        BookDto book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "showBook";
    }

    @PostMapping("/api/book/{bookId}/comment")
    public String saveComment(@Valid @ModelAttribute("text") String text,
                             @PathVariable("bookId") String bookId, Model model) {
        commentService.addComment(bookId, text);
        return getById(bookId, model);
    }
}
