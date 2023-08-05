package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataMongoTest
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("считать общее количество книг")
    @Test
    public void shouldCountBooks() {
        assertThat(bookRepository.count()).isEqualTo(3);
    }

    @DisplayName("искать все книги")
    @Test
    public void shouldSearchAllBooks() {
        assertThat(bookRepository.findAll()).hasSize(3);
    }

    @DisplayName("добавлять новые книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldAddBooks() {
        assertThat(bookRepository.count()).isEqualTo(3);
        Author author = new Author("1", "Aladdin Author");
        Genre genre = new Genre("1", "Fairy tale");
        Book book = new Book("Test", author, genre);
        String id = bookRepository.save(book).getId();
        assertThat(bookRepository.count()).isEqualTo(4);
        Optional<Book> bookOptional = bookRepository.findById(id);
        assertTrue(bookOptional.isPresent());
        assertEquals(bookOptional.get().getId(), id);
        assertEquals(bookOptional.get().getName(), "Test");
    }

    @DisplayName("искать книгу по id")
    @Test
    public void shouldSearchById() {
        Optional<Book> bookOptional = bookRepository.findById("1");
        assertTrue(bookOptional.isPresent());
        assertEquals(bookOptional.get().getId(), "1");
        assertEquals(bookOptional.get().getName(), "Aladdin");
    }

    @DisplayName("обновлять книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldUpdateBooks() {
        Author authorForUpdate = new Author("1", "Aladdin Author");
        Genre genreForUpdate = new Genre("1", "Fairy tale");
        Book updatedBook = new Book("2", "Test", authorForUpdate, genreForUpdate);
        bookRepository.save(updatedBook);

        Optional<Book> bookOptional = bookRepository.findById("2");
        assertTrue(bookOptional.isPresent());
        Book saved = bookOptional.get();
        assertEquals(saved.getName(), "Test");
        assertEquals(saved.getId(), "2");
        assertEquals(saved.getAuthor().getName(), "Aladdin Author");
    }

    @DisplayName("удалять книги")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldDeleteBooks() {
        assertThat(bookRepository.count()).isEqualTo(3);
        bookRepository.deleteById("1");
        bookRepository.deleteById("3");
        assertThat(bookRepository.count()).isEqualTo(1);
    }
}
