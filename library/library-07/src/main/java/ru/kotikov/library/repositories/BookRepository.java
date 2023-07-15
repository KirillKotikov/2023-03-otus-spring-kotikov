package ru.kotikov.library.repositories;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kotikov.library.models.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {

    Flux<Book> findAll();

    Mono<Book> findById(String id);

}
