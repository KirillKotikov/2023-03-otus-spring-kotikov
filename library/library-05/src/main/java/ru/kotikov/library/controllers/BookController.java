package ru.kotikov.library.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @GetMapping("/")
    public String getAll(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "allBooks";
    }

    @GetMapping("/get-by-id")
    public String getById(@Valid @ModelAttribute("id") String id, Model model) {
        BookDto book = bookService.getBookById(id);
        List<CommentDto> commentDtos = commentService.getByBookId(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", commentDtos);
        return "showBook";
    }

    @GetMapping("/add")
    public String add() {
        return "add";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String save(@Valid @ModelAttribute("book") BookDto bookDto) {
        System.out.println("bookDto = " + bookDto);
        bookService.saveBook(bookDto.getId(), bookDto.getName(), bookDto.getAuthorId(), bookDto.getGenreId());
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@Valid @ModelAttribute("id") String id) {
        bookService.deleteBookById(id);
        return "redirect:/";
    }

    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam(value = "commentId") String commentId,
                                @RequestParam(value = "bookId") String bookId, Model model) {
        commentService.deleteById(commentId);
        List<CommentDto> comments = commentService.getByBookId(bookId);
        BookDto book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        model.addAttribute("comments", comments);
        return "showBook";
    }

    @PostMapping("/comment/add")
    public String addComment(@Valid @ModelAttribute("text") String text,
                             @RequestParam("bookId") String bookId, Model model) {
        commentService.addComment(bookId, text);
        return getById(bookId, model);
    }
}
