package ru.kotikov.library.dao;

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
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @DisplayName("отображать всех авторов")
    @Test
    public void shouldShowAllAuthors() {
        assertThat(authorDaoJdbc.getAll()).hasSize(5);
        assertThat(authorDaoJdbc.getAll()).containsAll(List.of(
                new Author(1, "Aladdin author"),
                new Author(2, "Alice author"),
                new Author(3, "Winnie-the-Pooh author"),
                new Author(4, "Snow White author"),
                new Author(5, "Cinderella author")));
    }

    @DisplayName("искать автора по id")
    @Test
    public void shouldSearchById() {
        assertThat(authorDaoJdbc.getById(1)).isNotNull();
        assertThat(authorDaoJdbc.getById(1)).isEqualTo(new Author(1, "Aladdin author"));
    }
}
