package ru.kotikov.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами должно ")
@JdbcTest
@Import(JdbcBookDao.class)
public class JdbcBookDaoTest {
    @Autowired
    private JdbcBookDao jdbcBookDao;

    @DisplayName("считать общее количество книг")
    @Test
    public void shouldCountBooks() {
        assertThat(jdbcBookDao.count()).isEqualTo(2);
    }

    @DisplayName("отображать все книги")
    @Test
    public void shouldShowAllGenres() {
        assertThat(jdbcBookDao.getAll()).hasSize(2);
        assertThat(jdbcBookDao.getAll()).containsAll(List.of(
                new Book(1, "Aladdin",
                        new Author(1, "Aladdin author"),
                        new Genre(1, "Fairy tale")),
                new Book(2, "Alice in wonderland",
                        new Author(2, "Alice author"),
                        new Genre(2, "Fantasy"))));
    }

    @DisplayName("добавлять новые книги")
    @Test
    public void shouldAddBooks() {
        assertThat(jdbcBookDao.count()).isEqualTo(2);
        Author author = new Author(1, "Aladdin author");
        Genre genre = new Genre(1, "Fairy tale");
        Book book = new Book("Test", author, genre);
        jdbcBookDao.insert(book);
        assertThat(jdbcBookDao.count()).isEqualTo(3);
        assertThat(jdbcBookDao.getById(3)).isEqualTo(book);
    }

    @DisplayName("искать книгу по id")
    @Test
    public void shouldSearchById() {
        assertThat(jdbcBookDao.getById(1)).isNotNull();
        assertThat(jdbcBookDao.getById(1)).isEqualTo(new Book(1, "Aladdin",
                new Author(1, "Aladdin author"),
                new Genre(1, "Fairy tale")));
    }

    @DisplayName("обновлять книги")
    @Test
    public void shouldUpdateBooks() {
        Author authorForUpdate = new Author(1, "Aladdin author");
        Genre genreForUpdate = new Genre(1, "Fairy tale");
        Book updatedBook = new Book(2, "Test", authorForUpdate, genreForUpdate);
        assertThat(jdbcBookDao.update(updatedBook)).isEqualTo(updatedBook);
    }

    @DisplayName("удалять книги")
    @Test
    public void shouldDeleteBooks() {
        assertThat(jdbcBookDao.count()).isEqualTo(2);
        assertThat(jdbcBookDao.deleteById(1)).isEqualTo(1);
        assertThat(jdbcBookDao.deleteById(3)).isEqualTo(0);
        assertThat(jdbcBookDao.count()).isEqualTo(1);
        assertThat(jdbcBookDao.getAll()).contains(
                new Book(2, "Alice in wonderland",
                        new Author(2, "Alice author"),
                        new Genre(2, "Fantasy")));
    }
}
