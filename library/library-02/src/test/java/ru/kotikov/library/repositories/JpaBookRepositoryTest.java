package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataJpaTest
@Import(JpaBookRepository.class)
public class JpaBookRepositoryTest {
    @Autowired
    private JpaBookRepository jpaBookRepository;

    @DisplayName("считать общее количество книг")
    @Test
    public void shouldCountBooks() {
        assertThat(jpaBookRepository.count()).isEqualTo(2);
    }

    @DisplayName("искать все книги")
    @Test
    public void shouldShowAllGenres() {
        assertThat(jpaBookRepository.findAll()).hasSize(2);
        assertThat(jpaBookRepository.findAll()).containsAll(List.of(
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
        assertThat(jpaBookRepository.count()).isEqualTo(2);
        Author author = new Author(1, "Aladdin author");
        Genre genre = new Genre(1, "Fairy tale");
        Book book = new Book("Test", author, genre);
        jpaBookRepository.save(book);
        assertThat(jpaBookRepository.count()).isEqualTo(3);
        assertThat(jpaBookRepository.findById(3)).isPresent().get().isEqualTo(book);
    }

    @DisplayName("искать книгу по id")
    @Test
    public void shouldSearchById() {
        assertThat(jpaBookRepository.findById(1)).isNotNull();
        assertThat(jpaBookRepository.findById(1)).isPresent().get().isEqualTo(new Book(1, "Aladdin",
                new Author(1, "Aladdin author"),
                new Genre(1, "Fairy tale")));
    }

    @DisplayName("обновлять книги")
    @Test
    public void shouldUpdateBooks() {
        Author authorForUpdate = new Author(1, "Aladdin author");
        Genre genreForUpdate = new Genre(1, "Fairy tale");
        Book updatedBook = new Book(2, "Test", authorForUpdate, genreForUpdate);
        assertThat(jpaBookRepository.save(updatedBook)).isEqualTo(updatedBook);
    }

    @DisplayName("удалять книги")
    @Test
    public void shouldDeleteBooks() {
        assertThat(jpaBookRepository.count()).isEqualTo(2);
        jpaBookRepository.deleteById(1);
        jpaBookRepository.deleteById(3);
        assertThat(jpaBookRepository.count()).isEqualTo(1);
        assertThat(jpaBookRepository.findAll()).contains(
                new Book(2, "Alice in wonderland",
                        new Author(2, "Alice author"),
                        new Genre(2, "Fantasy")));
    }
}
