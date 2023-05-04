package ru.kotikov.library.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Маппер моделей должен ")
public class ModelMapperTest {

    private static  final Author author = new Author(1, "Author");
    private static  final Genre genre = new Genre(1, "Genre");

    @DisplayName(" мапить автора")
    @Test
    public void shouldMappingAuthor() {
        String mappedAuthorToString = ModelMapper.mapModelToString(author);
        assertThat(mappedAuthorToString)
                .isEqualTo("- id = " + author.getId() + ", имя автора = " + author.getName());
    }

    @DisplayName(" мапить книгу")
    @Test
    public void shouldMappingBook() {
        Book book = new Book(1, "book", author, genre);
        String mappedBookToString = ModelMapper.mapModelToString(book);
        assertThat(mappedBookToString)
                .isEqualTo("- id = " + book.getId() + ", название книги = " + book.getName() +
                        ", автор = " + book.getAuthor().getName() + ", жанр = " + book.getGenre().getName());
    }

    @DisplayName(" мапить жанр")
    @Test
    public void shouldMappingGenre() {
        String mappedGenreToString = ModelMapper.mapModelToString(genre);
        assertThat(mappedGenreToString)
                .isEqualTo("- id = " + genre.getId() + ", название жанра = " + genre.getName());
    }
}
