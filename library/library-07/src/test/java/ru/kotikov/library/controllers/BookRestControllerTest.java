package ru.kotikov.library.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kotikov.library.models.Author;
import ru.kotikov.library.models.Book;
import ru.kotikov.library.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Контроллер для книг должен ")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerTest {

    @LocalServerPort
    private int port;

    private WebClient client;

    private final Author author = new Author("1", "author");
    private final Genre genre = new Genre("2", "genre");
    private final Book book = new Book("3", "book", author, genre);

    @BeforeEach
    public void beforeEach() {
        client = WebClient.create(String.format("http://localhost:%d", port));
    }

    @Test
    @DisplayName("получать список книг.")
    void shouldGetAllBooks() {

        List<Book> result = client
                .get().uri("/api/book/")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Book.class)
                .toStream().toList();

        assertThat(result).hasSize(3);
    }


    @Test
    @DisplayName("получать книгу по id.")
    void shouldGetBookById() {

        Book result = client
                .get().uri("/api/book/1")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        assertThat(result.getId()).isEqualTo("1");
    }

    @Test
    @DisplayName("сохранять книгу.")
    void shouldSaveBook() {

        Book result = client
                .post().uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        assertThat(result.getId()).isEqualTo("3");
    }

    @Test
    @DisplayName("редактировать книгу")
    void shouldEditBook() {

        Book result = client
                .put().uri("/api/book")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        assertThat(result.getId()).isEqualTo("3");
    }


    // У меня не получается сделать этот тест :(
//    @Test
//    @DisplayName("удалять книгу.")
//    void shouldDeleteBook() {
//
//        ResponseEntity result = client
//                .delete().uri("/api/book/1")
//                .retrieve()
//                .bodyToMono(ResponseEntity.class)
//                .block();
//
////        Assertions.assertNotNull(result);
//        assertTrue(result.getStatusCode().is2xxSuccessful());
//    }

}

