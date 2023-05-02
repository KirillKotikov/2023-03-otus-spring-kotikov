package ru.kotikov.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.kotikov.library.Exceptions.EmptyInputFieldException;
import ru.kotikov.library.services.AuthorService;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.GenreService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @ShellMethod(value = "Вывод количества книг.", key = {"book-count", "bc"})
    public String showCount() {
        return bookService.countAllBooks();
    }

    @ShellMethod(value = "Вывод списка всех книг.", key = {"show-all-books", "sab"})
    public String showAllBooks() {
        return bookService.getAllBooks();
    }

    @ShellMethod(value = "Добавление книги. Пример: add-book <'book name'> <authorId> <genreId> ",
            key = {"add-book", "ab"})
    public String addBook(@ShellOption String bookName, @ShellOption Long authorId, @ShellOption Long genreId) {
        return bookService.addBook(bookName, authorId, genreId);
    }

    @ShellMethod(value = "Поиск книги по id. Пример: get-book-by-id <bookId> ", key = {"get-book-by-id", "gbbi"})
    public String getBookById(@ShellOption Long bookId) {
        if (bookId == null) {
            throw new EmptyInputFieldException("Входной параметр <bookId> пуст!");
        }
        return bookService.getBookById(bookId);
    }

    @ShellMethod(value = "Обновление книги. Пример: update-book <bookId> <'book name'> <authorId> <genreId> ",
            key = {"update-book", "ub"})
    public String updateBook(@ShellOption Long bookId,
                             @ShellOption(defaultValue = "") String bookName,
                             @ShellOption(defaultValue = "-1") Long authorId,
                             @ShellOption(defaultValue = "-1") Long genreId) {
        if (bookName.isBlank() && authorId == -1 && genreId == -1) {
            throw new EmptyInputFieldException("Изменяющие поля пустые!");
        }
        return bookService.updateBook(bookId, bookName, authorId, genreId);
    }

    @ShellMethod(value = "Удаление книги по id. Пример: delete-book-by-id <bookId> ",
            key = {"delete-book-by-id", "dbbi"})
    public String deleteBookById(@ShellOption Long bookId) {
        if (bookId == null) {
            throw new EmptyInputFieldException("Входной параметр <bookId> пуст!");
        }
        return bookService.deleteBookById(bookId);
    }

    @ShellMethod(value = "Вывод списка всех авторов.", key = {"show-all-authors", "saa"})
    public String showAllAuthors() {
        return authorService.getAllAuthors();
    }

    @ShellMethod(value = "Вывод списка всех жанров.", key = {"show-all-genres", "sag"})
    public String showAllGenres() {
        return genreService.getAllGenres();
    }
}
