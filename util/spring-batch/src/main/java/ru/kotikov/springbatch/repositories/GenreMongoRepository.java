package ru.kotikov.springbatch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.springbatch.models.GenreMongo;

public interface GenreMongoRepository extends MongoRepository<GenreMongo, String> {

}
