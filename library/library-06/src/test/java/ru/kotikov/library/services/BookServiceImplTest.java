package ru.kotikov.library.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.kotikov.library.Exceptions.DataNotFoundException;
import ru.kotikov.library.dtos.BookDto;
import ru.kotikov.library.dtos.BookWithCommentDto;
import ru.kotikov.library.dtos.CommentDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис книг должен ")
@SpringBootTest
public class BookServiceImplTest {
    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("искать все книги")
    @Test
    public void shouldShowAllBooks() {
        assertThat(bookService.getAllBooks()).hasSize(3);
    }

    @DisplayName("добавлять книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldAddBook() {
        BookDto actual = bookService.saveBook(
                new BookDto(null, "Test test", "1", null, "1", null));
        assertEquals(actual.getName(), "Test test");
        assertEquals(actual.getAuthorId(), "1");
        assertEquals(actual.getGenreId(), "1");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.saveBook(new BookDto(null, "Test test", "6", null,
                        "1", null))).getMessage();
        assertThat(exceptionMessage).isEqualTo("Автор с id = 6 в базе данных не найден!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при добавлении книги")
    @Test
    public void shouldThrowExceptionWhenAddedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.saveBook(new BookDto(null, "Test test", "1", null,
                        "6", null))).getMessage();
        assertThat(exceptionMessage).isEqualTo("Жанр с id = 6 в базе данных не найден!");
    }

    @DisplayName("найти книгу по id")
    @Test
    public void shouldFoundBookById() {
        BookDto bookById = bookService.getBookById("1");
        assertEquals(bookById.getName(), "Aladdin");
        assertEquals(bookById.getAuthorId(), "1");
        assertEquals(bookById.getGenreId(), "2");
    }

    @DisplayName("найти книгу по id с комментариями")
    @Test
    public void shouldFoundBookByIdWithComments() {
        BookWithCommentDto bookWithCommentDto = bookService.getBookWithCommentsByBookId("1");
        BookDto bookDto = bookWithCommentDto.getBookDto();
        List<CommentDto> commentDtoList = bookWithCommentDto.getCommentDtoList();
        assertEquals(bookDto.getName(), "Aladdin");
        assertEquals(bookDto.getAuthorId(), "1");
        assertEquals(bookDto.getGenreId(), "2");
        assertThat(commentDtoList).hasSize(2);
    }

    @DisplayName("выбросить ошибку, если не нашёл книгу по id")
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
        bookService.updateBook(new BookDto("1", "Test test", "2", null,
                "2", null));
        BookDto bookById = bookService.getBookById("1");
        assertEquals(bookById.getName(), "Test test");
        assertEquals(bookById.getAuthorId(), "2");
        assertEquals(bookById.getGenreId(), "2");
    }

    @DisplayName("выбросить ошибку, если не нашёл книгу по id при обновлении")
    @Test
    public void shouldThrowExceptionIfNotFoundBookByIdWhenUpdated() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.updateBook(new BookDto("20", "Test test", "2",
                        null, "2", null))).getMessage();
        assertThat(exceptionMessage).isEqualTo("Книга с id = 20 в базе данных не найдена!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся автор в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfAuthorNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.saveBook(new BookDto("2", "Test test", "6", null,
                        "2", null))).getMessage();
        assertThat(exceptionMessage).isEqualTo("Автор с id = 6 в базе данных не найден!");
    }

    @DisplayName("выбрасывать ошибку, если не нашёлся жанр в базе данных при обновлении книги")
    @Test
    public void shouldThrowExceptionWhenUpdatedBookIfGenreNotFound() {
        String exceptionMessage = assertThrows(DataNotFoundException.class,
                () -> bookService.saveBook(new BookDto("2", "Test test", "2", null,
                        "6", null))).getMessage();
        assertThat(exceptionMessage).isEqualTo("Жанр с id = 6 в базе данных не найден!");
    }

    @DisplayName("удалять книгу")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldDeleteBook() {
        assertThat(bookService.getAllBooks().size()).isEqualTo(3);
        bookService.deleteBookById("2");
        assertThat(bookService.getAllBooks().size()).isEqualTo(2);
    }

    @DisplayName("не найти книгу по id при удалении")
    @Test
    public void shouldNotFoundBookByIdWhenDeleted() {
        bookService.deleteBookById("20");
    }
}
