package ru.kotikov.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.library.models.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
