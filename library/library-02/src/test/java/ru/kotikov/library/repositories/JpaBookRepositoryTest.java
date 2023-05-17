package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void shouldSearchAllBooks() {
        assertThat(jpaBookRepository.findAll()).hasSize(2);
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
        Optional<Book> bookOptional = jpaBookRepository.findById(3);
        assertTrue(bookOptional.isPresent());
        assertEquals(bookOptional.get().getId(), 3);
        assertEquals(bookOptional.get().getName(), "Test");
    }

    @DisplayName("искать книгу по id")
    @Test
    public void shouldSearchById() {
        Optional<Book> bookOptional = jpaBookRepository.findById(1);
        assertTrue(bookOptional.isPresent());
        assertEquals(bookOptional.get().getId(), 1);
        assertEquals(bookOptional.get().getName(), "Aladdin");
    }

    @DisplayName("обновлять книги")
    @Test
    public void shouldUpdateBooks() {
        Author authorForUpdate = new Author(1, "Aladdin author");
        Genre genreForUpdate = new Genre(1, "Fairy tale");
        Book updatedBook = new Book(2, "Test", authorForUpdate, genreForUpdate);
        jpaBookRepository.save(updatedBook);

        Optional<Book> bookOptional = jpaBookRepository.findById(2);
        assertTrue(bookOptional.isPresent());
        Book saved = bookOptional.get();
        assertEquals(saved.getName(), "Test");
        assertEquals(saved.getId(), 2);
        assertEquals(saved.getAuthor().getName(), "Aladdin author");
    }

    @DisplayName("удалять книги")
    @Test
    public void shouldDeleteBooks() {
        assertThat(jpaBookRepository.count()).isEqualTo(2);
        jpaBookRepository.deleteById(1);
        jpaBookRepository.deleteById(3);
        assertThat(jpaBookRepository.count()).isEqualTo(1);
    }
}
