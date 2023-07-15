package ru.kotikov.library.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.kotikov.library.models.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Dao для работы с авторами должно ")
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName(" получать список всех авторов.")
    @Test
    public void shouldHasSizeEqualFive() {
        Flux<Author> all = authorRepository.findAll();

        StepVerifier
                .create(all)
                .assertNext(author -> assertNotNull(author.getId()))
                .assertNext(author -> assertNotNull(author.getId()))
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();

        Iterable<Author> authors = all.toIterable();
        assertThat(authors).hasSize(3);
    }

    @DisplayName("искать автора по id.")
    @Test
    public void shouldSearchById() {
        Mono<Author> authorMono = authorRepository.findById("1");

        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertEquals(author.getId(), "1"))
                .expectComplete()
                .verify();
    }

}
