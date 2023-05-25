package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.kotikov.library.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dao для работы с авторами должно ")
@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("размер списка всех авторов должен быть равен 5")
    @Test
    public void shouldHasSizeEqualFive() {
        assertThat(authorRepository.findAll()).hasSize(5);
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        Optional<Author> authorOptional = authorRepository.findById(1L);
        assertTrue(authorOptional.isPresent());
        assertEquals(authorOptional.get().getId(), 1, "Aladdin author");
        assertEquals(authorOptional.get().getName(), "Aladdin author");
    }

}
