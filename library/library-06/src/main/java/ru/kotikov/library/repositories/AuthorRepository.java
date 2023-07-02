package ru.kotikov.library.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.library.models.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
