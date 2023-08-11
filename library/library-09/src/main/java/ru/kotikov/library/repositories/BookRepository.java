package ru.kotikov.library.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAll();

    Optional<Book> findById(String id);
}
