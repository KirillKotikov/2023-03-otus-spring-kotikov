package ru.kotikov.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно ")
@JdbcTest
@Import(GenreDaoJdbc.class)
public class GenreDaoTest {
    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @DisplayName("отображать все жанры")
    @Test
    public void shouldShowAllGenres() {
        assertThat(genreDaoJdbc.getAll()).hasSize(5);
        assertThat(genreDaoJdbc.getAll()).containsAll(List.of(
                new Genre(1, "Fairy tale"),
                new Genre(2, "Fantasy"),
                new Genre(3, "Classic"),
                new Genre(4, "Love story"),
                new Genre(5, "Adventure")));
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        assertThat(genreDaoJdbc.getById(1)).isNotNull();
        assertThat(genreDaoJdbc.getById(1)).isEqualTo(new Genre(1, "Fairy tale"));
    }
}
