package ru.kotikov.blog.services;

import ru.kotikov.blog.dtos.PostDto;
import ru.kotikov.blog.models.Post;

import java.util.List;

public interface PostService {

    String POST = "Post";

    String ID = "id";

    boolean existsById(String postId);

    List<PostDto> getAllPostList();

    Post getPostById(String id);

    PostDto getPostDtoById(String id);

    List<PostDto> getPostListByUserLogin(String login);

    PostDto addPost(PostDto postDto);

    PostDto updatePost(PostDto postDto);

    void deletePostById(String id);

}
