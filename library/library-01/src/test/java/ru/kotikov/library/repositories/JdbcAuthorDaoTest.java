package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами должно ")
@JdbcTest
@Import(JdbcAuthorDao.class)
public class JdbcAuthorDaoTest {

    @Autowired
    private JdbcAuthorDao jdbcAuthorDao;

    @DisplayName("отображать всех авторов")
    @Test
    public void shouldShowAllAuthors() {
        assertThat(jdbcAuthorDao.getAll()).hasSize(5);
        assertThat(jdbcAuthorDao.getAll()).containsAll(List.of(
                new Author(1, "Aladdin author"),
                new Author(2, "Alice author"),
                new Author(3, "Winnie-the-Pooh author"),
                new Author(4, "Snow White author"),
                new Author(5, "Cinderella author")));
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        assertThat(jdbcAuthorDao.getById(1)).isNotNull();
        assertThat(jdbcAuthorDao.getById(1)).isEqualTo(new Author(1, "Aladdin author"));
    }
}
