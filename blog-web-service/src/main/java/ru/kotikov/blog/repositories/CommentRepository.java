package ru.kotikov.blog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.kotikov.blog.models.Comment;
import ru.kotikov.blog.models.User;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> getAllByPostId(String postId);

    List<Comment> getAllByPostIdAndUser(String postId, User user);

}
