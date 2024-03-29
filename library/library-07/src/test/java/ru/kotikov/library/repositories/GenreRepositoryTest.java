package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.kotikov.library.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dao для работы с жанрами должно ")
@DataMongoTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("читать все жанры")
    @Test
    public void shouldShowAllGenres() {
        assertThat(genreRepository.findAll().toIterable()).hasSize(3);
    }

    @DisplayName("искать жанр по id")
    @Test
    public void shouldSearchById() {
        Optional<Genre> genreOptional = genreRepository.findById("1").blockOptional();
        assertTrue(genreOptional.isPresent());
        assertEquals(genreOptional.get().getId(), "1");
        assertEquals(genreOptional.get().getName(), "Fairy tale");
    }
}
