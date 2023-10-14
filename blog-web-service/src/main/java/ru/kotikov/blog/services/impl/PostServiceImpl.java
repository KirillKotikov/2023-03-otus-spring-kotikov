package ru.kotikov.blog.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kotikov.blog.Utils.ErrorUtil;
import ru.kotikov.blog.Utils.UserUtils;
import ru.kotikov.blog.dtos.PostDto;
import ru.kotikov.blog.mappers.PostMapper;
import ru.kotikov.blog.models.Post;
import ru.kotikov.blog.models.User;
import ru.kotikov.blog.repositories.PostRepository;
import ru.kotikov.blog.services.PostService;
import ru.kotikov.blog.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final UserService userService;

    @Override
    public boolean existsById(String postId) {
        return postRepository.existsById(postId);
    }

    @Override
    public List<PostDto> getAllPostList() {
        return postRepository.findAll().stream()
                .map(postMapper::postToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPostById(String id) {
        Optional<Post> postOptionalById = postRepository.findById(id);
        if (postOptionalById.isPresent()) {
            return postOptionalById.get();
        } else {
            ErrorUtil.notExistsBy(POST, ID, id);
            return null;
        }
    }

    @Override
    public PostDto getPostDtoById(String id) {
        Optional<Post> postOptionalById = postRepository.findById(id);
        if (postOptionalById.isPresent()) {
            return postMapper.postToPostDto(postOptionalById.get());
        } else {
            ErrorUtil.notExistsBy(POST, ID, id);
            return null;
        }
    }

    @Override
    public List<PostDto> getPostListByUserLogin(String login) {
        User user = userService.getUserByLogin(login);
        if (user == null) {
            ErrorUtil.notExistsBy(UserService.USER, UserService.LOGIN, login);
        }
        return postMapper.postListToPostDtoList(postRepository.getByUser(user));
    }

    @Override
    public PostDto addPost(PostDto postDto) {
        String loginAuthenticated = UserUtils.getLoginAuthenticated();
        User userByLogin = userService.getUserByLogin(postDto.getUserLogin());
        if (userByLogin == null) {
            ErrorUtil.notExistsBy(
                    UserService.USER, UserService.LOGIN, postDto.getUserLogin()
            );
        }
        Post post = postMapper.postDtoToPost(postDto);
        post.setUser(userByLogin);
        Post savedPost = postRepository.save(post);
        return postMapper.postToPostDto(savedPost);
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        Optional<Post> postById = postRepository.findById(postDto.getId());
        if (postById.isPresent()) {
            Post post = postById.get();
            post.setHeader(postDto.getHeader());
            post.setText(postDto.getText());
            return postMapper.postToPostDto(postRepository.save(post));
        } else {
            ErrorUtil.notExistsBy(POST, ID, postDto.getId());
        }
        return null;
    }

    @Override
    public void deletePostById(String id) {
        if (!postRepository.existsById(id)) {
            ErrorUtil.notExistsBy(POST, ID, id);
        }
        postRepository.deleteById(id);
    }

}
