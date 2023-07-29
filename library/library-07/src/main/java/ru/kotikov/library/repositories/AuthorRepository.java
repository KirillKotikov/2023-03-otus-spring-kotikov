package ru.kotikov.library.repositories;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kotikov.library.models.Author;

public interface AuthorRepository extends ReactiveCrudRepository<Author, String> {

    Flux<Author> findAll();

    Mono<Author> findById(String id);

}
