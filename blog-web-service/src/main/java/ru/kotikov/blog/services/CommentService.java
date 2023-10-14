package ru.kotikov.blog.services;

import ru.kotikov.blog.dtos.CommentDto;

import java.util.List;

public interface CommentService {

    String COMMENT = "Comment";

    String ID = "ID";

    List<CommentDto> getCommentListByPostId(String postId);

    List<CommentDto> getCommentListByPostIdAndUserLogin(String postId, String login);

    CommentDto addComment(CommentDto commentDto);

    CommentDto updateComment(CommentDto commentDto);

    void deleteComment(String commentId);

}
