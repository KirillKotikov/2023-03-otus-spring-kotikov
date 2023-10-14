package ru.kotikov.blog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.blog.models.Post;
import ru.kotikov.blog.models.User;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> getByUser(User user);

}