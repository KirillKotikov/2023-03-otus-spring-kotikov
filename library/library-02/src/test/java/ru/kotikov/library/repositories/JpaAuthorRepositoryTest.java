package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами должно ")
@DataJpaTest
@Import(JpaAuthorRepository.class)
public class JpaAuthorRepositoryTest {

    @Autowired
    private JpaAuthorRepository jdbcAuthorDao;

    @DisplayName("отображать всех авторов")
    @Test
    public void shouldShowAllAuthors() {
        assertThat(jdbcAuthorDao.findAll()).hasSize(5);
        assertThat(jdbcAuthorDao.findAll()).containsAll(List.of(
                new Author(1, "Aladdin author"),
                new Author(2, "Alice author"),
                new Author(3, "Winnie-the-Pooh author"),
                new Author(4, "Snow White author"),
                new Author(5, "Cinderella author")));
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        assertThat(jdbcAuthorDao.findById(1)).isNotNull();
        assertThat(jdbcAuthorDao.findById(1)).isPresent().get().isEqualTo(new Author(1, "Aladdin author"));
    }
}
