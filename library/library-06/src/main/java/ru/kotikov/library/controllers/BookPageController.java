package ru.kotikov.library.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kotikov.library.dtos.AuthorDto;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.GenreDto;
import ru.kotikov.library.services.AuthorService;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookService bookService;

    @GetMapping("/api/book")
    public String listBooksPage() {
        return "allBooks";
    }

    @GetMapping("/api/book/showPage/{bookId}")
    public String getById(@PathVariable("bookId") String id, Model model) {
        model.addAttribute("bookId", id);
        return "showBook";
    }

    @GetMapping("/api/book/addingPage")
    public String addingPage(Model model) {
        List<AuthorDto> authors = authorService.getAll();
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "addingPage";
    }

    @GetMapping("/api/book/editPage/{id}")
    public String editPage(@PathVariable("id") String id, Model model) {
        BookDto book = bookService.getBookById(id);
        model.addAttribute("book", book);
        List<AuthorDto> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        List<GenreDto> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "editPage";
    }

//    @PostMapping("/api/book")
//    public String saveBook(@Valid @ModelAttribute("book") BookDto bookDto) {
//        bookService.saveBook(bookDto);
//        return "redirect:/api/book";
//    }
//
//    @PostMapping("/api/book/delete") // как я понял thymeleaf поддерживает только get и post запросы
//    public String deleteBook(@ModelAttribute("id") String id) {
//        bookService.deleteBookById(id);
//        return "redirect:/api/book";
//    }
//
//    @PostMapping("/api/book/{bookId}/comment/delete")
//    public String deleteComment(@RequestParam(value = "commentId") String commentId,
//                                @PathVariable(value = "bookId") String bookId, Model model) {
//        commentService.deleteById(commentId);
//        BookWithCommentDto bookWithCommentDto = bookService.getBookWithCommentsByBookId(bookId);
//        model.addAttribute("bookWithCommentDto", bookWithCommentDto);
//        return "showBook";
//    }
//
//    @PostMapping("/api/book/{bookId}/comment")
//    public String saveComment(@Valid @ModelAttribute CommentDto comment, Model model) {
//        commentService.addComment(comment);
//        return getById(comment.getBookId(), model);
//    }
}