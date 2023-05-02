package ru.kotikov.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.kotikov.library.Exceptions.DataNotFountException;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис книг должен ")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("отображать общее количество книг")
    @Test
    public void shouldShowCountOfBooks() {
        assertThat(bookService.countAllBooks()).isEqualTo("Общее количество книг = 2");
    }

    @DisplayName("отображать список книг")
    @Test
    public void shouldShowAllBooks() {
        assertThat(bookService.getAllBooks()).isEqualTo(
                """
                        - id = 1, название = 'Aladdin', автор = Aladdin author, жанр = Fairy tale
                        - id = 2, название = 'Alice in wonderland', автор = Alice author, жанр = Fantasy
                        """);
    }

    @DisplayName("добавлять книгу")
    @Test
    public void shouldAddBook() {
        assertThat(bookService.addBook("Test test", 1, 1)).isEqualTo(
                "Книга {- id = 3, название = 'Test test', автор = Aladdin author, жанр = Fairy tale} успешно сохранена!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFountException.class,
                () -> bookService.addBook("Test test", 6, 1)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Author by id = 6 not found");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFountException.class,
                () -> bookService.addBook("Test test", 1, 6)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Genre by id = 6 not found");
    }

    @DisplayName("найти книгу по id")
    @Test
    public void shouldFoundBookById() {
        String bookById = bookService.getBookById(1);
        Book expectedBook = new Book(1, "Aladdin",
                new Author(1, "Aladdin author"),
                new Genre(1, "Fairy tale"));
        assertThat(bookById).isEqualTo(expectedBook.toString());
    }

    @DisplayName("не найти книгу по id")
    @Test
    public void shouldNotFoundBookById() {
        assertThat(bookService.getBookById(20)).isEqualTo("Книга с id = 20 не найдена!");
    }

    @DisplayName("обновлять книгу")
    @Test
    public void shouldUpdateBook() {
        Book expectedBook = new Book(1, "Test test",
                new Author(2, "Alice author"), new Genre(2, "Fantasy"));
        assertThat(bookService.updateBook(1, "Test test", 2L, 2L)).isEqualTo(
                "Обновлённая книга: " + expectedBook);
    }

    @DisplayName("не найти книгу по id при обновлении")
    @Test
    public void shouldNotFoundBookByIdWhenUpdated() {
        assertThat(bookService.updateBook(20, "Test test", 2L, 2L))
                .isEqualTo("Книга с id = 20 не найдена!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFountException.class,
                () -> bookService.updateBook(2, "Test test", 6L, 2L)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Author by id = 6 not found");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFountException.class,
                () -> bookService.updateBook(2, "Test test", 2L, 6L)).getMessage();
        assertThat(exceptionMessage).isEqualTo("Genre by id = 6 not found");
    }

    @DisplayName("удалять книгу")
    @Test
    public void shouldDeleteBook() {
        assertThat(bookService.countAllBooks()).isEqualTo("Общее количество книг = 2");
        String deleteBookByIdStr = bookService.deleteBookById(2);
        assertThat(deleteBookByIdStr).isEqualTo("Книга с id = 2 успешно удалена!");
        assertThat(bookService.countAllBooks()).isEqualTo("Общее количество книг = 1");
    }

    @DisplayName("не найти книгу по id при удалении")
    @Test
    public void shouldNotFoundBookByIdWhenDeleted() {
        assertThat(bookService.deleteBookById(20))
                .isEqualTo("Книга с id = 20 не найдена!");
    }
}
