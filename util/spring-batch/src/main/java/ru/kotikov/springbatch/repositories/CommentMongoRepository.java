package ru.kotikov.springbatch.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.springbatch.models.BookMongo;
import ru.kotikov.springbatch.models.CommentMongo;

import java.util.List;
import java.util.Optional;

public interface CommentMongoRepository extends MongoRepository<CommentMongo, String> {

    List<CommentMongo> findByBook(BookMongo book);

    Optional<CommentMongo> findById(String id);

    void deleteByBookId(String id);

}
