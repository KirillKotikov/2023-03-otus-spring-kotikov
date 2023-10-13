package ru.kotikov.blog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.blog.models.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByLogin(String login);

    User findByLogin(String login);

    int deleteUserByLogin(String login);

}
