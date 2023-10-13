package ru.kotikov.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotikov.blog.Utils.ErrorUtil;
import ru.kotikov.blog.dtos.CommentDto;
import ru.kotikov.blog.mappers.CommentMapper;
import ru.kotikov.blog.models.Comment;
import ru.kotikov.blog.models.Post;
import ru.kotikov.blog.models.User;
import ru.kotikov.blog.repositories.CommentRepository;
import ru.kotikov.blog.services.CommentService;
import ru.kotikov.blog.services.PostService;
import ru.kotikov.blog.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final PostService postService;

    private final UserService userService;

    @Override
    public List<CommentDto> getCommentListByPostId(String postId) {
        if (!postService.existsById(postId)) {
            ErrorUtil.notExistsBy(COMMENT, ID, postId);
        }
        List<Comment> allByPostId = commentRepository.getAllByPostId(postId);
        return commentMapper.commentListToCommentDtoList(allByPostId);
    }

    @Override
    public List<CommentDto> getCommentListByPostIdAndUserLogin(
            String postId, String login) {
        if (!postService.existsById(postId)) {
            ErrorUtil.notExistsBy(COMMENT, ID, postId);
        }
        User userByLogin = userService.getUserByLogin(login);
        if (userByLogin == null) {
            ErrorUtil.notExistsBy(UserService.USER, UserService.LOGIN, login);
        }
        List<Comment> allByPostIdAndUserId =
                commentRepository.getAllByPostIdAndUser(postId, userByLogin);
        return commentMapper.commentListToCommentDtoList(allByPostIdAndUserId);
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        User userByLogin = userService.getUserByLogin(commentDto.getUserLogin());
        Post postById = postService.getPostById(commentDto.getPostId());
        Comment comment = commentMapper.commentDtoToComment(commentDto);
        comment.setId(null);
        comment.setUser(userByLogin);
        comment.setPost(postById);
        Comment savedComment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(savedComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto) {
        Optional<Comment> optionalComment =
                commentRepository.findById(commentDto.getId());
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setText(commentDto.getText());
            Comment savedComment = commentRepository.save(comment);
            return commentMapper.commentToCommentDto(savedComment);
        } else {
            ErrorUtil.notExistsBy(COMMENT, ID, commentDto.getId());
            return null;
        }
    }

    @Override
    public void deleteComment(String id) {
        if (!commentRepository.existsById(id)) {
            ErrorUtil.notExistsBy(COMMENT, ID, id);
        }
        commentRepository.deleteById(id);
    }

}
