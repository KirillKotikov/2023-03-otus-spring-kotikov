package ru.kotikov.library.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.kotikov.library.models.Genre;

public interface GenreRepository extends ReactiveCrudRepository<Genre, String> {

}
