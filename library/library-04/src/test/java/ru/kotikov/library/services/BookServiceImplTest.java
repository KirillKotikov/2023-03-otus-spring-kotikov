package ru.kotikov.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.models.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис книг должен ")
@SpringBootTest
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("отображать общее количество книг")
    @Test
    public void shouldShowCountOfBooks() {
        assertThat(bookService.countAllBooks()).isEqualTo(3);
    }

    @DisplayName("искать все книги")
    @Test
    public void shouldShowAllBooks() {
        assertThat(bookService.getAllBooks()).hasSize(3);
    }

    @DisplayName("добавлять книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldAddBook() {
        Book actual = bookService.addBook("Test test", "1", "1");
        assertEquals(actual.getName(), "Test test");
        assertEquals(actual.getAuthor().getId(), "1");
        assertEquals(actual.getGenre().getId(), "1");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.addBook("Test test", "6", "1")).getMessage();
        assertThat(exceptionMessage).isEqualTo("Автор с id = 6 в базе данных не найден!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.addBook("Test test", "1", "6")).getMessage();
        assertThat(exceptionMessage).isEqualTo("Жанр с id = 6 в базе данных не найден!");
    }

    @DisplayName("найти книгу по id")
    @Test
    public void shouldFoundBookById() {
        Book bookById = bookService.getBookById("1");
        assertEquals(bookById.getName(), "Aladdin");
        assertEquals(bookById.getAuthor().getId(), "1");
        assertEquals(bookById.getGenre().getId(), "2");
    }

    @DisplayName("выброоить ошибку, если не нашёл книгу по id")
    @Test
    public void shouldNotFoundBookById() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.getBookById("20")).getMessage();
        assertThat(exceptionMessage).isEqualTo("Книга с id = 20 в базе данных не найдена!");
    }

    @DisplayName("обновлять книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldUpdateBook() {
        bookService.updateBook("1", "Test test", "2", "2");
        Book bookById = bookService.getBookById("1");
        assertEquals(bookById.getName(), "Test test");
        assertEquals(bookById.getAuthor().getId(), "2");
        assertEquals(bookById.getGenre().getId(), "2");
    }

    @DisplayName("выброоить ошибку, если не нашёл книгу по id при обновлении")
    @Test
    public void shouldThrowExceptionIfNotFoundBookByIdWhenUpdated() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook("20", "Test test", "2", "2")).getMessage();
        assertThat(exceptionMessage).isEqualTo("Книга с id = 20 в базе данных не найдена!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook("2", "Test test", "6", "2")).getMessage();
        assertThat(exceptionMessage).isEqualTo("Автор с id = 6 в базе данных не найден!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook("2", "Test test", "2", "6")).getMessage();
        assertThat(exceptionMessage).isEqualTo("Жанр с id = 6 в базе данных не найден!");
    }

    @DisplayName("удалять книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldDeleteBook() {
        assertThat(bookService.countAllBooks()).isEqualTo(3);
        bookService.deleteBookById("2");
        assertThat(bookService.countAllBooks()).isEqualTo(2);
    }

    @DisplayName("не найти книгу по id при удалении")
    @Test
    public void shouldNotFoundBookByIdWhenDeleted() {
        bookService.deleteBookById("20");
    }
}
