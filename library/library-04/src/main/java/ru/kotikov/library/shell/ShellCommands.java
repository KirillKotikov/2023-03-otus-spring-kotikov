package ru.kotikov.library.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Comment;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.services.AuthorService;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.CommentService;
import ru.kotikov.library.services.GenreService;
import ru.kotikov.library.utils.ModelMapper;

import java.util.List;

@ShellComponent
public class ShellCommands {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    public ShellCommands(BookService bookService, AuthorService authorService,
                         GenreService genreService, CommentService commentService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    @ShellMethod(value = "Вывод количества книг.", key = {"book-count", "bc"})
    public String showCount() {
        return "Общее количество книг = " + bookService.countAllBooks();
    }

    @ShellMethod(value = "Вывод списка всех книг.", key = {"show-all-books", "sab"})
    public String showAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        return ModelMapper.formatBooks(allBooks);
    }

    @ShellMethod(value = "Добавление книги. Пример: add-book <'book name'> <authorId> <genreId> ",
            key = {"add-book", "ab"})
    public String addBook(@ShellOption String bookName, @ShellOption String authorId, @ShellOption String genreId) {
        Book savedBook;
            savedBook = bookService.addBook(bookName, authorId, genreId);
        return "Книга {" + ModelMapper.mapModelToString(savedBook) + "} успешно сохранена!";
    }

    @ShellMethod(value = "Поиск книги по id. Пример: get-book-by-id <bookId> ", key = {"get-book-by-id", "gbbi"})
    @Transactional(readOnly = true)
    public String getBookById(@ShellOption String bookId) {
        Book bookById;
            bookById = bookService.getBookById(bookId);
        return ModelMapper.mapModelToString(bookById);
    }

    @ShellMethod(value = "Обновление книги. Пример: update-book <bookId> <'book name'> <authorId> <genreId> ",
            key = {"update-book", "ub"})
    public String updateBook(@ShellOption String bookId, @ShellOption String bookName,
                             @ShellOption String authorId, @ShellOption String genreId) {
        Book updatedBook;
            updatedBook = bookService.updateBook(bookId, bookName, authorId, genreId);
        return "Обновлённая книга: " + ModelMapper.mapModelToString(updatedBook);
    }

    @ShellMethod(value = "Удаление книги по id. Пример: delete-book-by-id <bookId> ",
            key = {"delete-book-by-id", "dbbi"})
    public String deleteBookById(@ShellOption String bookId) {
        bookService.deleteBookById(bookId);
        return "Книга с id = " + bookId + " успешно удалена!";
    }

    @ShellMethod(value = "Вывод списка всех авторов.", key = {"show-all-authors", "saa"})
    public String showAllAuthors() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Author author : authorService.getAllAuthors()) {
            stringBuilder.append(ModelMapper.mapModelToString(author)).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "Вывод списка всех жанров.", key = {"show-all-genres", "sag"})
    public String showAllGenres() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Genre genre : genreService.getAllGenres()) {
            stringBuilder.append(ModelMapper.mapModelToString(genre)).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "Вывод комментариев книги. Пример: show-book-comments <bookId>",
            key = {"show-book-comments", "sbc"})
    public String showBookComments(@ShellOption String bookId) {
        StringBuilder stringBuilder = new StringBuilder();
        Book bookById = bookService.getBookById(bookId);
        for (Comment comment : commentService.getByBook(bookById)) {
            stringBuilder.append(ModelMapper.mapModelToString(comment)).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "Добавление комментария. Пример: add-comment <bookId> <'text'> ",
            key = {"add-comment", "ac"})
    public String addComment(@ShellOption String bookId, @ShellOption String text) {
        Book book = bookService.getBookById(bookId);
        Comment comment = commentService.addComment(new Comment(text, book));
        return "Комментарий {" + ModelMapper.mapCommentToString(comment, book.getName()) + "} успешно добавлен!";
    }

    @ShellMethod(value = "Обновление комментария. Пример: edit-comment <commentId> <'text'> ",
            key = {"edit-comment", "ec"})
    public String editComment(@ShellOption String commentId, @ShellOption String text) {
        Comment editedComment = commentService.updateCommentText(commentId, text);
        return "Обновлённый комментарий: " + ModelMapper.mapModelToString(editedComment);
    }

    @ShellMethod(value = "Поиск комментария по id. Пример: get-comment-by-id <commentId> ",
            key = {"get-comment-by-id", "gcbi"})
    public String getCommentById(@ShellOption String commentId) {
        Comment commentById = commentService.getById(commentId);
        return ModelMapper.mapModelToString(commentById);
    }

    @ShellMethod(value = "Удаление комментария по id. Пример: delete-comment-by-id <commentId> ",
            key = {"delete-comment-by-id", "dcbi"})
    public String deleteCommentById(@ShellOption String commentId) {
        commentService.deleteById(commentId);
        return "Комментарий с id = " + commentId + " успешно удалён!";
    }
}
