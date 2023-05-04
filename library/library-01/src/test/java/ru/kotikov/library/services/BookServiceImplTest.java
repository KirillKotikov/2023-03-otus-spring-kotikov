package ru.kotikov.library.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис книг должен ")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookServiceImplTest {
    private Author author1;

    private Author author2;

    private Genre genre1;

    private Genre genre2;

    private Book book1;

    private Book book2;

    @Autowired
    private BookServiceImpl bookService;

    @BeforeEach
    public void initialize() {
        author1 = new Author(1, "Aladdin author");
        author2 = new Author(2, "Alice author");
        genre1 = new Genre(1, "Fairy tale");
        genre2 = new Genre(2, "Fantasy");
        book1 = new Book(1, "Aladdin", author1, genre1);
        book2 = new Book(2, "Alice in wonderland", author2, genre2);
    }

    @DisplayName("отображать общее количество книг")
    @Test
    public void shouldShowCountOfBooks() {
        assertThat(bookService.countAllBooks()).isEqualTo(2);
    }

    @DisplayName("отображать список книг")
    @Test
    public void shouldShowAllBooks() {
        assertThat(bookService.getAllBooks()).containsAll(
                List.of(book1, book2));
    }

    @DisplayName("добавлять книгу")
    @Test
    public void shouldAddBook() throws DataNotFoundException {
        Book expectedBook = new Book(3, "Test test", author1, genre1);
        assertThat(bookService.addBook("Test test", 1, 1)).isEqualTo(expectedBook);
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.addBook("Test test", 6, 1)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Автор с id = 6 в базе данных не найден!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.addBook("Test test", 1, 6)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Жанр с id = 6 в базе данных не найден!");
    }

    @DisplayName("найти книгу по id")
    @Test
    public void shouldFoundBookById() throws DataNotFoundException {
        Book bookById = bookService.getBookById(1);
        assertThat(bookById).isEqualTo(book1);
    }

    @DisplayName("выброоить ошибку, если не нашёл книгу по id")
    @Test
    public void shouldNotFoundBookById() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.getBookById(20)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Книга с id = 20 в базе данных не найдена!");
    }

    @DisplayName("обновлять книгу")
    @Test
    public void shouldUpdateBook() throws DataNotFoundException {
        Book expectedBook = new Book(1, "Test test", author2, genre2);
        assertThat(bookService.updateBook(1, "Test test", 2L, 2L)).isEqualTo(expectedBook);
    }

    @DisplayName("выброоить ошибку, если не нашёл книгу по id при обновлении")
    @Test
    public void shouldThrowExceptionIfNotFoundBookByIdWhenUpdated() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook(20, "Test test", 2L, 2L)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Книга с id = 20 в базе данных не найдена!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook(2, "Test test", 6L, 2L)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Автор с id = 6 в базе данных не найден!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook(2, "Test test", 2L, 6L)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Жанр с id = 6 в базе данных не найден!");
    }

    @DisplayName("удалять книгу")
    @Test
    public void shouldDeleteBook() {
        assertThat(bookService.countAllBooks()).isEqualTo(2);
        int affectedRows = bookService.deleteBookById(2);
        assertThat(affectedRows).isGreaterThan(0);
        assertThat(bookService.countAllBooks()).isEqualTo(1);
    }

    @DisplayName("не найти книгу по id при удалении")
    @Test
    public void shouldNotFoundBookByIdWhenDeleted() {
        assertThat(bookService.deleteBookById(20)).isEqualTo(0);
    }
}
