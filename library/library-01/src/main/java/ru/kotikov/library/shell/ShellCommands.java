package ru.kotikov.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.constants.ExceptionMessages;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;
import ru.kotikov.library.services.AuthorService;
import ru.kotikov.library.services.BookService;
import ru.kotikov.library.services.GenreService;
import ru.kotikov.library.utils.ModelMapper;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @ShellMethod(value = "Вывод количества книг.", key = {"book-count", "bc"})
    public String showCount() {
        return "Общее количество книг = " + bookService.countAllBooks();
    }

    @ShellMethod(value = "Вывод списка всех книг.", key = {"show-all-books", "sab"})
    public String showAllBooks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : bookService.getAllBooks()) {
            stringBuilder.append(ModelMapper.mapModelToString(book)).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "Добавление книги. Пример: add-book <'book name'> <authorId> <genreId> ",
            key = {"add-book", "ab"})
    public String addBook(@ShellOption String bookName, @ShellOption Long authorId, @ShellOption Long genreId) {
        Book savedBook;
        try {
            savedBook = bookService.addBook(bookName, authorId, genreId);
        } catch (DataNotFoundException e) {
            return e.getMessage();
        }
        return "Книга {" + ModelMapper.mapModelToString(savedBook) + "} успешно сохранена!";
    }

    @ShellMethod(value = "Поиск книги по id. Пример: get-book-by-id <bookId> ", key = {"get-book-by-id", "gbbi"})
    public String getBookById(@ShellOption Long bookId) {
        Book bookById;
        try {
            bookById = bookService.getBookById(bookId);
        } catch (DataNotFoundException e) {
            return e.getMessage();
        }
        return ModelMapper.mapModelToString(bookById);
    }

    @ShellMethod(value = "Обновление книги. Пример: update-book <bookId> <'book name'> <authorId> <genreId> ",
            key = {"update-book", "ub"})
    public String updateBook(@ShellOption Long bookId, @ShellOption String bookName,
                             @ShellOption Long authorId, @ShellOption Long genreId) {
        Book updatedBook;
        try {
            updatedBook = bookService.updateBook(bookId, bookName, authorId, genreId);
        } catch (DataNotFoundException e) {
            return e.getMessage();
        }
        return "Обновлённая книга: " + updatedBook;
    }

    @ShellMethod(value = "Удаление книги по id. Пример: delete-book-by-id <bookId> ",
            key = {"delete-book-by-id", "dbbi"})
    public String deleteBookById(@ShellOption Long bookId) {
        return bookService.deleteBookById(bookId) > 0 ? "Книга с id = " + bookId + " успешно удалена!"
                : String.format(ExceptionMessages.BOOK_NOT_FOUND, bookId);
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
}
