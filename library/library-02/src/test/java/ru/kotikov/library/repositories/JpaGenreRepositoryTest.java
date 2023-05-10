package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами должно ")
@DataJpaTest
@Import(JpaGenreRepository.class)
public class JpaGenreRepositoryTest {
    @Autowired
    private JpaGenreRepository jdbcGenreDao;

    @DisplayName("отображать все жанры")
    @Test
    public void shouldShowAllGenres() {
        assertThat(jdbcGenreDao.findAll()).hasSize(5);
        assertThat(jdbcGenreDao.findAll()).containsAll(List.of(
                new Genre(1, "Fairy tale"),
                new Genre(2, "Fantasy"),
                new Genre(3, "Classic"),
                new Genre(4, "Love story"),
                new Genre(5, "Adventure")));
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        assertThat(jdbcGenreDao.findById(1)).isNotNull();
        assertThat(jdbcGenreDao.findById(1)).isPresent().get().isEqualTo(new Genre(1, "Fairy tale"));
    }
}
