package ru.kotikov.library.dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("dto книги должно ")
public class BookDtoTest {

    @Test
    @DisplayName("конвертировать model в dto")
    public void shouldConvertModelToDto() {
        Book book = new Book("1", "name",
                new Author("2", "author"), new Genre("3", "genre"));
        BookDto bookDto = BookDto.toDto(book);
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getName(), bookDto.getName());
        assertEquals(book.getAuthor().getId(), bookDto.getAuthorId());
        assertEquals(book.getAuthor().getName(), bookDto.getAuthorName());
        assertEquals(book.getGenre().getId(), bookDto.getGenreId());
        assertEquals(book.getGenre().getName(), bookDto.getGenreName());
    }

    @Test
    @DisplayName("конвертировать dto в model")
    public void shouldConvertDtoToModel() {
        BookDto bookDto = new BookDto("1", "name",
                "2", "author", "3", "genre");
        Book book = bookDto.toModel();
        assertEquals(book.getId(), bookDto.getId());
        assertEquals(book.getName(), bookDto.getName());
        assertEquals(book.getAuthor().getId(), bookDto.getAuthorId());
        assertEquals(book.getAuthor().getName(), bookDto.getAuthorName());
        assertEquals(book.getGenre().getId(), bookDto.getGenreId());
        assertEquals(book.getGenre().getName(), bookDto.getGenreName());
    }
}
