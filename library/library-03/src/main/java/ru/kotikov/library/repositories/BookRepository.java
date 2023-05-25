package ru.kotikov.library.repositories;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kotikov.library.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book-genre-author-graph")
    List<Book> findAll();

    @EntityGraph(value = "book-genre-author-graph")
    Optional<Book> findById(long id);
}
