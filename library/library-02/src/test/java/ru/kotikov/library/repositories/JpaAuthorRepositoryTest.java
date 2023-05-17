package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Dao для работы с авторами должно ")
@DataJpaTest
@Import(JpaAuthorRepository.class)
public class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository jdbcAuthorDao;

    @DisplayName("размер списка всех авторов должен быть равен 5")
    @Test
    public void shouldHasSizeEqualFive() {
        assertThat(jdbcAuthorDao.findAll()).hasSize(5);
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        Optional<Author> authorOptional = jdbcAuthorDao.findById(1);
        assertTrue(authorOptional.isPresent());
        assertEquals(authorOptional.get().getId(), 1, "Aladdin author");
        assertEquals(authorOptional.get().getName(), "Aladdin author");
    }

}
