package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("считать общее количество книг.")
    @Test
    public void shouldCountBooks() {
        assertThat(bookRepository.count().block()).isEqualTo(3);
    }

    @DisplayName("искать все книги.")
    @Test
    public void shouldSearchAllBooks() {
        assertThat(bookRepository.findAll().toIterable()).hasSize(3);
    }

    @DisplayName("добавлять новые книги.")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldAddBooks() {
        assertThat(bookRepository.count().block()).isEqualTo(3);
        Author author = new Author("1", "Aladdin Author");
        Genre genre = new Genre("1", "Fairy tale");
        Book book = new Book("Test", author, genre);
        String id = bookRepository.save(book).block().getId();
        assertThat(bookRepository.count().block()).isEqualTo(4);

        Book foundBook = bookRepository.findById(id).block();
        assertEquals(foundBook.getId(), id);
        assertEquals(foundBook.getName(), "Test");
    }

    @DisplayName("искать книгу по id.")
    @Test
    public void shouldSearchById() {
        Book book = bookRepository.findById("1").block();
        assertEquals(book.getId(), "1");
        assertEquals(book.getName(), "Aladdin");
    }

    @DisplayName("обновлять книги.")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldUpdateBooks() {
        Author authorForUpdate = new Author("1", "Aladdin Author");
        Genre genreForUpdate = new Genre("1", "Fairy tale");
        Book updatedBook = new Book("2", "Test", authorForUpdate, genreForUpdate);
        bookRepository.save(updatedBook).subscribe();

        Book saved = bookRepository.findById("2").block();
        assertEquals(saved.getName(), "Test");
        assertEquals(saved.getId(), "2");
        assertEquals(saved.getAuthor().getName(), "Aladdin Author");
    }

    @DisplayName("удалять книги.")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void shouldDeleteBooks() {
        assertThat(bookRepository.count().block()).isEqualTo(3);
        bookRepository.deleteById("3").block();
        bookRepository.deleteById("1").block();
        assertThat(bookRepository.count().block()).isEqualTo(1);
    }

}
