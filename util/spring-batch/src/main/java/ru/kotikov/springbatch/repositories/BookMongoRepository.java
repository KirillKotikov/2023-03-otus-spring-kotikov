package ru.kotikov.springbatch.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.springbatch.models.BookMongo;

import java.util.List;
import java.util.Optional;

public interface BookMongoRepository extends MongoRepository<BookMongo, String> {

    List<BookMongo> findAll();

    Optional<BookMongo> findById(String id);

}
