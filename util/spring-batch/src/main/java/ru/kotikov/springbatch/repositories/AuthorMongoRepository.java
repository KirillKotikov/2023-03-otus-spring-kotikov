package ru.kotikov.springbatch.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.springbatch.models.AuthorMongo;

public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, String> {

}
