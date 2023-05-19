package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dao для работы с жанрами должно ")
@DataJpaTest
@Import(JpaGenreRepository.class)
public class JpaGenreRepositoryTest {
    @Autowired
    private JpaGenreRepository jdbcGenreDao;

    @DisplayName("читать все жанры")
    @Test
    public void shouldShowAllGenres() {
        assertThat(jdbcGenreDao.findAll()).hasSize(5);
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        Optional<Genre> genreOptional = jdbcGenreDao.findById(1);
        assertTrue(genreOptional.isPresent());
        assertEquals(genreOptional.get().getId(), 1);
        assertEquals(genreOptional.get().getName(), "Fairy tale");
    }
}
