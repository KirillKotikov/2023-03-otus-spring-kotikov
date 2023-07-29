package ru.kotikov.library.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.library.models.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String login);

}
